package model

// DashboardSummary represents the aggregated financial data for the user.
type DashboardSummary struct {
	TotalBalance      float64            `json:"totalBalance"`
	TotalIncome       float64            `json:"totalIncome"`
	TotalExpenses     float64            `json:"totalExpenses"`
	ExpenseByCategory map[string]float64 `json:"expenseByCategory"`
}
