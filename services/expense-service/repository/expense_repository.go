package repository

import (
	"context"
	"financier/expense-service/model"
	"time"

	"github.com/google/uuid"
	"gorm.io/gorm"
)

// ExpenseRepository defines the interface for expense data operations.
type ExpenseRepository interface {
	Create(ctx context.Context, expense *model.Expense) (string, error)
	GetByID(ctx context.Context, id string) (*model.Expense, error)
	GetByUserID(ctx context.Context, userID string) ([]*model.Expense, error)
	GetRecent(ctx context.Context, userID string, limit int) ([]*model.Expense, error)
	GetSummary(ctx context.Context, userID string) (*model.DashboardSummary, error)
	Update(ctx context.Context, id string, expense *model.Expense) (int64, error)
	Delete(ctx context.Context, id string) (int64, error)
}

// postgresExpenseRepository is the PostgreSQL implementation of ExpenseRepository.
type postgresExpenseRepository struct {
	db *gorm.DB
}

// NewPostgresExpenseRepository creates a new repository for expenses.
func NewPostgresExpenseRepository(db *gorm.DB) *postgresExpenseRepository {
	return &postgresExpenseRepository{
		db: db,
	}
}

func (r *postgresExpenseRepository) Create(ctx context.Context, expense *model.Expense) (string, error) {
	if expense.ID == uuid.Nil {
		expense.ID = uuid.New()
	}
	expense.CreatedAt = time.Now()
	expense.UpdatedAt = time.Now()
	if err := r.db.WithContext(ctx).Create(expense).Error; err != nil {
		return "", err
	}
	return expense.ID.String(), nil
}

func (r *postgresExpenseRepository) GetByID(ctx context.Context, id string) (*model.Expense, error) {
	var expense model.Expense
	if err := r.db.WithContext(ctx).First(&expense, "id = ?", id).Error; err != nil {
		return nil, err
	}
	return &expense, nil
}

func (r *postgresExpenseRepository) GetByUserID(ctx context.Context, userID string) ([]*model.Expense, error) {
	var expenses []*model.Expense
	if err := r.db.WithContext(ctx).Where("user_id = ?", userID).Find(&expenses).Error; err != nil {
		return nil, err
	}
	return expenses, nil
}

func (r *postgresExpenseRepository) GetRecent(ctx context.Context, userID string, limit int) ([]*model.Expense, error) {
	var expenses []*model.Expense
	if err := r.db.WithContext(ctx).Where("user_id = ?", userID).Order("date DESC").Limit(limit).Find(&expenses).Error; err != nil {
		return nil, err
	}
	return expenses, nil
}

func (r *postgresExpenseRepository) GetSummary(ctx context.Context, userID string) (*model.DashboardSummary, error) {
	summary := &model.DashboardSummary{
		ExpenseByCategory: make(map[string]float64),
	}

	// 1. Get sums by type
	type Result struct {
		Type  string  `gorm:"column:type"`
		Total float64 `gorm:"column:total"`
	}
	var results []Result
	err := r.db.WithContext(ctx).Model(&model.Expense{}).
		Select("type, sum(amount) as total").
		Where("user_id = ?", userID).
		Group("type").
		Scan(&results).Error
	if err != nil {
		return nil, err
	}

	for _, result := range results {
		if result.Type == "INCOME" {
			summary.TotalIncome = result.Total
		} else {
			summary.TotalExpenses += result.Total
		}
	}
	summary.TotalBalance = summary.TotalIncome - summary.TotalExpenses

	// 2. Get category breakdown for expenses
	type CatResult struct {
		Category string  `gorm:"column:category"`
		Total    float64 `gorm:"column:total"`
	}
	var catResults []CatResult
	err = r.db.WithContext(ctx).Model(&model.Expense{}).
		Select("category, sum(amount) as total").
		Where("user_id = ? AND type != ?", userID, "INCOME").
		Group("category").
		Scan(&catResults).Error
	if err != nil {
		return nil, err
	}

	for _, result := range catResults {
		summary.ExpenseByCategory[result.Category] = result.Total
	}

	return summary, nil
}

func (r *postgresExpenseRepository) Update(ctx context.Context, id string, expense *model.Expense) (int64, error) {
	result := r.db.WithContext(ctx).Model(&model.Expense{}).Where("id = ?", id).Updates(map[string]interface{}{
		"amount":      expense.Amount,
		"category":    expense.Category,
		"description": expense.Description,
		"date":        expense.Date,
		"updated_at":  time.Now(),
	})
	if result.Error != nil {
		return 0, result.Error
	}
	return result.RowsAffected, nil
}

func (r *postgresExpenseRepository) Delete(ctx context.Context, id string) (int64, error) {
	result := r.db.WithContext(ctx).Where("id = ?", id).Delete(&model.Expense{})
	if result.Error != nil {
		return 0, result.Error
	}
	return result.RowsAffected, nil
}
