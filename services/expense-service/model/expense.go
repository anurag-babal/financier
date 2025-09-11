package model

import (
	"time"

	"go.mongodb.org/mongo-driver/bson/primitive"
)

// Expense represents a single expense record.
type Expense struct {
	ID          primitive.ObjectID `bson:"_id,omitempty" json:"id"`
	UserID      string             `bson:"userId" json:"userId"`
	Amount      float64            `bson:"amount" json:"amount"`
	Category    string             `bson:"category" json:"category"`
	Description string             `bson:"description,omitempty" json:"description"`
	Date        time.Time          `bson:"date" json:"date"`
	CreatedAt   time.Time          `bson:"createdAt" json:"createdAt"`
	UpdatedAt   time.Time          `bson:"updatedAt" json:"updatedAt"`
}
