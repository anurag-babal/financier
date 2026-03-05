package handler

import (
	"bytes"
	"context"
	"encoding/json"
	"financier/expense-service/model"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/gin-gonic/gin"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
)

// MockRepository is a mock implementation of the ExpenseRepository interface
type MockRepository struct {
	mock.Mock
}

func (m *MockRepository) Create(ctx context.Context, expense *model.Expense) (string, error) {
	args := m.Called(ctx, expense)
	return args.String(0), args.Error(1)
}

func (m *MockRepository) GetByID(ctx context.Context, id string) (*model.Expense, error) {
	args := m.Called(ctx, id)
	return args.Get(0).(*model.Expense), args.Error(1)
}

func (m *MockRepository) GetByUserID(ctx context.Context, userID string) ([]*model.Expense, error) {
	args := m.Called(ctx, userID)
	return args.Get(0).([]*model.Expense), args.Error(1)
}

func (m *MockRepository) GetRecent(ctx context.Context, userID string, limit int) ([]*model.Expense, error) {
	args := m.Called(ctx, userID, limit)
	return args.Get(0).([]*model.Expense), args.Error(1)
}

func (m *MockRepository) GetSummary(ctx context.Context, userID string) (*model.DashboardSummary, error) {
	args := m.Called(ctx, userID)
	return args.Get(0).(*model.DashboardSummary), args.Error(1)
}

func (m *MockRepository) Update(ctx context.Context, id string, expense *model.Expense) (int64, error) {
	args := m.Called(ctx, id, expense)
	return int64(args.Int(0)), args.Error(1)
}

func (m *MockRepository) Delete(ctx context.Context, id string) (int64, error) {
	args := m.Called(ctx, id)
	return int64(args.Int(0)), args.Error(1)
}

func TestCreateExpense(t *testing.T) {
	gin.SetMode(gin.TestMode)
	
	mockRepo := new(MockRepository)
	h := NewExpenseHandler(mockRepo)

	router := gin.Default()
	router.POST("/api/expenses", func(c *gin.Context) {
		c.Set("userID", "test-user-123")
		h.CreateExpense(c)
	})

	expense := model.Expense{
		Amount:   100.50,
		Category: "Food",
		Type:     "EXPENSE",
	}
	jsonValue, _ := json.Marshal(expense)

	mockRepo.On("Create", mock.Anything, mock.AnythingOfType("*model.Expense")).Return("new-uuid", nil)

	req, _ := http.NewRequest("POST", "/api/expenses", bytes.NewBuffer(jsonValue))
	w := httptest.NewRecorder()
	router.ServeHTTP(w, req)

	assert.Equal(t, http.StatusCreated, w.Code)
	var response map[string]string
	json.Unmarshal(w.Body.Bytes(), &response)
	assert.Equal(t, "new-uuid", response["id"])
	mockRepo.AssertExpectations(t)
}

func TestGetSummary(t *testing.T) {
	gin.SetMode(gin.TestMode)
	
	mockRepo := new(MockRepository)
	h := NewExpenseHandler(mockRepo)

	router := gin.Default()
	router.GET("/api/expenses/summary", func(c *gin.Context) {
		c.Set("userID", "test-user-123")
		h.GetSummary(c)
	})

	expectedSummary := &model.DashboardSummary{
		TotalBalance: 500,
		TotalIncome:  1000,
		TotalExpenses: 500,
	}

	mockRepo.On("GetSummary", mock.Anything, "test-user-123").Return(expectedSummary, nil)

	req, _ := http.NewRequest("GET", "/api/expenses/summary", nil)
	w := httptest.NewRecorder()
	router.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)
	
	var actualSummary model.DashboardSummary
	json.Unmarshal(w.Body.Bytes(), &actualSummary)
	assert.Equal(t, expectedSummary.TotalBalance, actualSummary.TotalBalance)
	mockRepo.AssertExpectations(t)
}

func TestGetUserExpenses(t *testing.T) {
	gin.SetMode(gin.TestMode)
	mockRepo := new(MockRepository)
	h := NewExpenseHandler(mockRepo)

	router := gin.Default()
	router.GET("/api/expenses", func(c *gin.Context) {
		c.Set("userID", "test-user-123")
		h.GetUserExpenses(c)
	})

	expectedExpenses := []*model.Expense{
		{Amount: 50},
		{Amount: 100},
	}
	mockRepo.On("GetByUserID", mock.Anything, "test-user-123").Return(expectedExpenses, nil)

	req, _ := http.NewRequest("GET", "/api/expenses", nil)
	w := httptest.NewRecorder()
	router.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)
	var actual []*model.Expense
	json.Unmarshal(w.Body.Bytes(), &actual)
	assert.Len(t, actual, 2)
	mockRepo.AssertExpectations(t)
}

func TestGetExpenseByID(t *testing.T) {
	gin.SetMode(gin.TestMode)
	mockRepo := new(MockRepository)
	h := NewExpenseHandler(mockRepo)

	router := gin.Default()
	router.GET("/api/expenses/:id", func(c *gin.Context) {
		c.Set("userID", "test-user-123")
		h.GetExpenseByID(c)
	})

	id := "uuid-1"
	expense := &model.Expense{UserID: "test-user-123", Amount: 50}
	mockRepo.On("GetByID", mock.Anything, id).Return(expense, nil)

	req, _ := http.NewRequest("GET", "/api/expenses/"+id, nil)
	w := httptest.NewRecorder()
	router.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)
	var actual model.Expense
	json.Unmarshal(w.Body.Bytes(), &actual)
	assert.Equal(t, float64(50), actual.Amount)
	mockRepo.AssertExpectations(t)
}

func TestUpdateExpense(t *testing.T) {
	gin.SetMode(gin.TestMode)
	mockRepo := new(MockRepository)
	h := NewExpenseHandler(mockRepo)

	router := gin.Default()
	router.PUT("/api/expenses/:id", func(c *gin.Context) {
		c.Set("userID", "test-user-123")
		h.UpdateExpense(c)
	})

	id := "uuid-1"
	existing := &model.Expense{UserID: "test-user-123"}
	mockRepo.On("GetByID", mock.Anything, id).Return(existing, nil)
	mockRepo.On("Update", mock.Anything, id, mock.AnythingOfType("*model.Expense")).Return(1, nil)

	updateData := model.Expense{Amount: 75}
	jsonBytes, _ := json.Marshal(updateData)

	req, _ := http.NewRequest("PUT", "/api/expenses/"+id, bytes.NewBuffer(jsonBytes))
	w := httptest.NewRecorder()
	router.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)
	mockRepo.AssertExpectations(t)
}

func TestDeleteExpense(t *testing.T) {
	gin.SetMode(gin.TestMode)
	mockRepo := new(MockRepository)
	h := NewExpenseHandler(mockRepo)

	router := gin.Default()
	router.DELETE("/api/expenses/:id", func(c *gin.Context) {
		c.Set("userID", "test-user-123")
		h.DeleteExpense(c)
	})

	id := "uuid-1"
	existing := &model.Expense{UserID: "test-user-123"}
	mockRepo.On("GetByID", mock.Anything, id).Return(existing, nil)
	mockRepo.On("Delete", mock.Anything, id).Return(1, nil)

	req, _ := http.NewRequest("DELETE", "/api/expenses/"+id, nil)
	w := httptest.NewRecorder()
	router.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)
	mockRepo.AssertExpectations(t)
}
