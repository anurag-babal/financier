package repository

import (
	"context"
	"financier/expense-service/model"
	"regexp"
	"testing"
	"time"

	"github.com/DATA-DOG/go-sqlmock"
	"github.com/google/uuid"
	"github.com/stretchr/testify/assert"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func setupMockDB(t *testing.T) (*gorm.DB, sqlmock.Sqlmock) {
	db, mock, err := sqlmock.New()
	assert.NoError(t, err)

	gormDB, err := gorm.Open(postgres.New(postgres.Config{
		Conn: db,
	}), &gorm.Config{})
	assert.NoError(t, err)

	return gormDB, mock
}

func TestCreateExpenseRepo(t *testing.T) {
	db, mock := setupMockDB(t)
	repo := NewPostgresExpenseRepository(db)

	userID := "user-1"
	expense := &model.Expense{
		UserID:   userID,
		Amount:   50.0,
		Category: "Snacks",
		Type:     "EXPENSE",
		Date:     time.Now(),
	}

	mock.ExpectBegin()
	// Note: GORM's generated insert order for this struct:
	// "id", "user_id", "type", "amount", "category", "description", "date", "created_at", "updated_at"
	mock.ExpectQuery(regexp.QuoteMeta(`INSERT INTO "expenses"`)).
		WithArgs(sqlmock.AnyArg(), userID, "EXPENSE", 50.0, "Snacks", "", sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg()).
		WillReturnRows(sqlmock.NewRows([]string{"id"}).AddRow(uuid.New()))
	mock.ExpectCommit()

	id, err := repo.Create(context.Background(), expense)

	if err != nil {
		t.Logf("Create error: %v", err)
	}
	assert.NoError(t, err)
	assert.NotEmpty(t, id)
	assert.NoError(t, mock.ExpectationsWereMet())
}

func TestGetByIDRepo(t *testing.T) {
	db, mock := setupMockDB(t)
	repo := NewPostgresExpenseRepository(db)

	id := uuid.New()
	mock.ExpectQuery(regexp.QuoteMeta(`SELECT * FROM "expenses" WHERE id = $1`)).
		WithArgs(id.String(), 1).
		WillReturnRows(sqlmock.NewRows([]string{"id", "user_id", "amount", "category"}).
			AddRow(id, "user-1", 50.0, "Snacks"))

	expense, err := repo.GetByID(context.Background(), id.String())

	assert.NoError(t, err)
	assert.NotNil(t, expense)
	assert.Equal(t, id, expense.ID)
	assert.NoError(t, mock.ExpectationsWereMet())
}

func TestGetSummaryRepo(t *testing.T) {
	db, mock := setupMockDB(t)
	repo := NewPostgresExpenseRepository(db)

	userID := "user-summary-1"

	mock.ExpectQuery(regexp.QuoteMeta(`SELECT type, sum(amount) as total FROM "expenses" WHERE user_id = $1 GROUP BY "type"`)).
		WithArgs(userID).
		WillReturnRows(sqlmock.NewRows([]string{"type", "total"}).
			AddRow("INCOME", 1000.0).
			AddRow("EXPENSE", 350.0))

	mock.ExpectQuery(regexp.QuoteMeta(`SELECT category, sum(amount) as total FROM "expenses" WHERE user_id = $1 AND type != $2 GROUP BY "category"`)).
		WithArgs(userID, "INCOME").
		WillReturnRows(sqlmock.NewRows([]string{"category", "total"}).
			AddRow("Food", 300.0).
			AddRow("Rent", 50.0))

	summary, err := repo.GetSummary(context.Background(), userID)

	assert.NoError(t, err)
	assert.Equal(t, 1000.0, summary.TotalIncome)
	assert.Equal(t, 350.0, summary.TotalExpenses)
	assert.Equal(t, 650.0, summary.TotalBalance)
	assert.Equal(t, 300.0, summary.ExpenseByCategory["Food"])
	assert.Equal(t, 50.0, summary.ExpenseByCategory["Rent"])
	assert.NoError(t, mock.ExpectationsWereMet())
}

func TestUpdateExpenseRepo(t *testing.T) {
	db, mock := setupMockDB(t)
	repo := NewPostgresExpenseRepository(db)

	id := uuid.New().String()
	expense := &model.Expense{
		Amount:   150.0,
		Category: "Updated Food",
	}

	mock.ExpectBegin()
	mock.ExpectExec(regexp.QuoteMeta(`UPDATE "expenses" SET`)).
		WithArgs(150.0, "Updated Food", sqlmock.AnyArg(), "", sqlmock.AnyArg(), id).
		WillReturnResult(sqlmock.NewResult(0, 1))
	mock.ExpectCommit()

	rowsAffected, err := repo.Update(context.Background(), id, expense)

	assert.NoError(t, err)
	assert.Equal(t, int64(1), rowsAffected)
	assert.NoError(t, mock.ExpectationsWereMet())
}

func TestDeleteExpenseRepo(t *testing.T) {
	db, mock := setupMockDB(t)
	repo := NewPostgresExpenseRepository(db)

	id := uuid.New().String()

	mock.ExpectBegin()
	mock.ExpectExec(regexp.QuoteMeta(`DELETE FROM "expenses" WHERE id = $1`)).
		WithArgs(id).
		WillReturnResult(sqlmock.NewResult(0, 1))
	mock.ExpectCommit()

	rowsAffected, err := repo.Delete(context.Background(), id)

	assert.NoError(t, err)
	assert.Equal(t, int64(1), rowsAffected)
	assert.NoError(t, mock.ExpectationsWereMet())
}
