class Expense {
  final String id;
  final String userId;
  final String type;
  final double amount;
  final String category;
  final String description;
  final DateTime date;

  Expense({
    required this.id,
    required this.userId,
    this.type = 'EXPENSE',
    required this.amount,
    required this.category,
    required this.description,
    required this.date,
  });

  factory Expense.fromJson(Map<String, dynamic> json) {
    return Expense(
      id: json['id'],
      userId: json['userId'],
      type: json['type'] ?? 'EXPENSE',
      amount: (json['amount'] as num).toDouble(),
      category: json['category'],
      description: json['description'] ?? '',
      date: DateTime.parse(json['date']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'userId': userId,
      'type': type,
      'amount': amount,
      'category': category,
      'description': description,
      'date': date.toIso8601String(),
    };
  }
}
