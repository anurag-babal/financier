package model

import (
	"time"

	"github.com/google/uuid"
)

// Expense represents a single transaction (income or expense) record.
type Expense struct {
	ID          uuid.UUID `gorm:"type:uuid;primaryKey" json:"id"`
	UserID      string    `gorm:"index" json:"userId"`
	Type        string    `json:"type"` // "INCOME" or "EXPENSE"
	Amount      float64   `json:"amount"`
	Category    string    `json:"category"`
	Description string    `json:"description,omitempty"`
	Date        time.Time `json:"date"`
	CreatedAt   time.Time `json:"createdAt"`
	UpdatedAt   time.Time `json:"updatedAt"`
}
