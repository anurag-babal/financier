class DashboardSummary {
  final double totalBalance;
  final double totalIncome;
  final double totalExpenses;
  final Map<String, double> expenseByCategory;

  DashboardSummary({
    required this.totalBalance,
    required this.totalIncome,
    required this.totalExpenses,
    required this.expenseByCategory,
  });

  factory DashboardSummary.fromJson(Map<String, dynamic> json) {
    Map<String, double> categoryMap = {};
    if (json['expenseByCategory'] != null) {
      json['expenseByCategory'].forEach((key, value) {
        categoryMap[key] = (value as num).toDouble();
      });
    }
    
    return DashboardSummary(
      totalBalance: (json['totalBalance'] as num?)?.toDouble() ?? 0.0,
      totalIncome: (json['totalIncome'] as num?)?.toDouble() ?? 0.0,
      totalExpenses: (json['totalExpenses'] as num?)?.toDouble() ?? 0.0,
      expenseByCategory: categoryMap,
    );
  }
}
