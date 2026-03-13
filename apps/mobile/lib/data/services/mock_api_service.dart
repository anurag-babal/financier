import '../models/transaction_model.dart';
import '../models/user_model.dart';
import '../models/dashboard_summary_model.dart';

class MockApiService {
  final List<Transaction> _mockTransactions = [
    Transaction(
      id: '1',
      userId: 'user123',
      amount: 45.50,
      category: 'Food',
      description: 'Lunch at Cafe',
      date: DateTime.now().subtract(const Duration(hours: 2)),
    ),
    Transaction(
      id: '2',
      userId: 'user123',
      amount: 20.00,
      category: 'Transport',
      description: 'Taxi to office',
      date: DateTime.now().subtract(const Duration(hours: 5)),
    ),
    Transaction(
      id: '3',
      userId: 'user123',
      amount: 150.00,
      category: 'Shopping',
      description: 'Groceries',
      date: DateTime.now().subtract(const Duration(days: 1)),
    ),
    Transaction(
      id: '4',
      userId: 'user123',
      amount: 12.99,
      category: 'Entertainment',
      description: 'Movie ticket',
      date: DateTime.now().subtract(const Duration(days: 2)),
    ),
  ];

  final User _mockUser = User(
    id: 1,
    name: 'Anurag Babal',
    username: 'anuragbabal',
    email: 'anurag@example.com',
    profilePictureUrl: 'https://i.pravatar.cc/150?u=anurag',
    bio: 'Finance enthusiast and software developer.',
  );

  Future<List<Transaction>> getTransactions() async {
    await Future.delayed(const Duration(milliseconds: 800)); // Simulate network delay
    return List.from(_mockTransactions);
  }

  Future<User> getUserProfile() async {
    await Future.delayed(const Duration(milliseconds: 500));
    return _mockUser;
  }

  Future<void> addTransaction(Transaction transaction) async {
    await Future.delayed(const Duration(milliseconds: 1000));
    _mockTransactions.insert(0, transaction);
  }

  Future<void> register(String name, String email, String password, String currency) async {
    await Future.delayed(const Duration(seconds: 1));
  }

  Future<String> login(String email, String password) async {
    await Future.delayed(const Duration(seconds: 1));
    return 'mock-token';
  }

  Future<void> updateProfile(User user) async {
    await Future.delayed(const Duration(seconds: 1));
  }

  Future<DashboardSummary> getDashboardSummary() async {
    await Future.delayed(const Duration(milliseconds: 500));
    return DashboardSummary(
      totalBalance: 12450.80,
      totalIncome: 14200.00,
      totalExpenses: 1749.20,
      expenseByCategory: {'Food': 45.50, 'Transport': 20.00, 'Shopping': 150.00, 'Entertainment': 12.99},
    );
  }

  Future<List<Transaction>> getRecentTransactions() async {
    await Future.delayed(const Duration(milliseconds: 500));
    return List.from(_mockTransactions);
  }
}
