package repository

import (
	"context"
	"financier/expense-service/model"
	"time"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

// ExpenseRepository defines the interface for expense data operations.
type ExpenseRepository interface {
	Create(ctx context.Context, expense *model.Expense) (string, error)
	GetByID(ctx context.Context, id string) (*model.Expense, error)
	GetByUserID(ctx context.Context, userID string) ([]*model.Expense, error)
	Update(ctx context.Context, id string, expense *model.Expense) (int64, error)
	Delete(ctx context.Context, id string) (int64, error)
}

// mongoExpenseRepository is the MongoDB implementation of ExpenseRepository.
type mongoExpenseRepository struct {
	collection *mongo.Collection
}

// NewMongoExpenseRepository creates a new repository for expenses.
func NewMongoExpenseRepository(db *mongo.Database) *mongoExpenseRepository {
	return &mongoExpenseRepository{
		collection: db.Collection("expenses"),
	}
}

func (r *mongoExpenseRepository) Create(ctx context.Context, expense *model.Expense) (string, error) {
	expense.ID = primitive.NewObjectID()
	expense.CreatedAt = time.Now()
	expense.UpdatedAt = time.Now()
	_, err := r.collection.InsertOne(ctx, expense)
	if err != nil {
		return "", err
	}
	return expense.ID.Hex(), nil
}

func (r *mongoExpenseRepository) GetByID(ctx context.Context, id string) (*model.Expense, error) {
	objID, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		return nil, err
	}

	var expense model.Expense
	err = r.collection.FindOne(ctx, bson.M{"_id": objID}).Decode(&expense)
	if err != nil {
		return nil, err
	}
	return &expense, nil
}

func (r *mongoExpenseRepository) GetByUserID(ctx context.Context, userID string) ([]*model.Expense, error) {
	var expenses []*model.Expense
	cursor, err := r.collection.Find(ctx, bson.M{"userId": userID})
	if err != nil {
		return nil, err
	}
	defer cursor.Close(ctx)

	for cursor.Next(ctx) {
		var expense model.Expense
		if err := cursor.Decode(&expense); err != nil {
			return nil, err
		}
		expenses = append(expenses, &expense)
	}

	return expenses, nil
}

func (r *mongoExpenseRepository) Update(ctx context.Context, id string, expense *model.Expense) (int64, error) {
	objID, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		return 0, err
	}

	update := bson.M{
		"$set": bson.M{
			"amount":      expense.Amount,
			"category":    expense.Category,
			"description": expense.Description,
			"date":        expense.Date,
			"updatedAt":   time.Now(),
		},
	}

	result, err := r.collection.UpdateOne(ctx, bson.M{"_id": objID}, update)
	if err != nil {
		return 0, err
	}
	return result.ModifiedCount, nil
}

func (r *mongoExpenseRepository) Delete(ctx context.Context, id string) (int64, error) {
	objID, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		return 0, err
	}

	result, err := r.collection.DeleteOne(ctx, bson.M{"_id": objID})
	if err != nil {
		return 0, err
	}
	return result.DeletedCount, nil
}
