package handler

import (
	"financier/expense-service/model"
	"financier/expense-service/repository"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
)

// ExpenseHandler handles the HTTP requests for expenses.
type ExpenseHandler struct {
	repo repository.ExpenseRepository
}

// NewExpenseHandler creates a new handler with the given repository.
func NewExpenseHandler(repo repository.ExpenseRepository) *ExpenseHandler {
	return &ExpenseHandler{repo: repo}
}

// CreateExpense handles the creation of a new expense.
func (h *ExpenseHandler) CreateExpense(c *gin.Context) {
	var expense model.Expense
	if err := c.ShouldBindJSON(&expense); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	// Get UserID from context (set by middleware)
	userID, exists := c.Get("userID")
	if !exists {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "User ID not found in token"})
		return
	}
	expense.UserID = userID.(string)
	
	// Default date if not provided
	if expense.Date.IsZero() {
		expense.Date = time.Now()
	}

	id, err := h.repo.Create(c.Request.Context(), &expense)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to create expense"})
		return
	}

	c.JSON(http.StatusCreated, gin.H{"id": id})
}

// GetUserExpenses retrieves all expenses for the authenticated user.
func (h *ExpenseHandler) GetUserExpenses(c *gin.Context) {
	userID, exists := c.Get("userID")
	if !exists {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "User ID not found in token"})
		return
	}

	expenses, err := h.repo.GetByUserID(c.Request.Context(), userID.(string))
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to retrieve expenses"})
		return
	}

	c.JSON(http.StatusOK, expenses)
}

// GetExpenseByID retrieves a single expense by its ID.
func (h *ExpenseHandler) GetExpenseByID(c *gin.Context) {
	id := c.Param("id")
	userID, _ := c.Get("userID")

	expense, err := h.repo.GetByID(c.Request.Context(), id)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "Expense not found"})
		return
	}

	// Security check: ensure the user owns this expense
	if expense.UserID != userID.(string) {
		c.JSON(http.StatusForbidden, gin.H{"error": "You are not authorized to view this expense"})
		return
	}

	c.JSON(http.StatusOK, expense)
}

// UpdateExpense updates an existing expense.
func (h *ExpenseHandler) UpdateExpense(c *gin.Context) {
	id := c.Param("id")
	userID, _ := c.Get("userID")

	// First, verify ownership
	existingExpense, err := h.repo.GetByID(c.Request.Context(), id)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "Expense not found"})
		return
	}
	if existingExpense.UserID != userID.(string) {
		c.JSON(http.StatusForbidden, gin.H{"error": "You are not authorized to update this expense"})
		return
	}

	var expense model.Expense
	if err := c.ShouldBindJSON(&expense); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	
	// Ensure the user ID is not changed
	expense.UserID = userID.(string)

	modifiedCount, err := h.repo.Update(c.Request.Context(), id, &expense)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to update expense"})
		return
	}

	c.JSON(http.StatusOK, gin.H{"modifiedCount": modifiedCount})
}

// DeleteExpense deletes an expense.
func (h *ExpenseHandler) DeleteExpense(c *gin.Context) {
	id := c.Param("id")
	userID, _ := c.Get("userID")

	// First, verify ownership
	existingExpense, err := h.repo.GetByID(c.Request.Context(), id)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "Expense not found"})
		return
	}
	if existingExpense.UserID != userID.(string) {
		c.JSON(http.StatusForbidden, gin.H{"error": "You are not authorized to delete this expense"})
		return
	}

	deletedCount, err := h.repo.Delete(c.Request.Context(), id)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to delete expense"})
		return
	}

	c.JSON(http.StatusOK, gin.H{"deletedCount": deletedCount})
}
