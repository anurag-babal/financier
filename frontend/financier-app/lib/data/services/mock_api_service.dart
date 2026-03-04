import 'dart:async';
import '../models/expense_model.dart';
import '../models/user_model.dart';

class MockApiService {
  final List<Expense> _mockExpenses = [
    Expense(
      id: '1',
      userId: 'user123',
      amount: 45.50,
      category: 'Food',
      description: 'Lunch at Cafe',
      date: DateTime.now().subtract(const Duration(hours: 2)),
    ),
    Expense(
      id: '2',
      userId: 'user123',
      amount: 20.00,
      category: 'Transport',
      description: 'Taxi to office',
      date: DateTime.now().subtract(const Duration(hours: 5)),
    ),
    Expense(
      id: '3',
      userId: 'user123',
      amount: 150.00,
      category: 'Shopping',
      description: 'Groceries',
      date: DateTime.now().subtract(const Duration(days: 1)),
    ),
    Expense(
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

  Future<List<Expense>> getExpenses() async {
    await Future.delayed(const Duration(milliseconds: 800)); // Simulate network delay
    return List.from(_mockExpenses);
  }

  Future<User> getUserProfile() async {
    await Future.delayed(const Duration(milliseconds: 500));
    return _mockUser;
  }

  Future<void> addExpense(Expense expense) async {
    await Future.delayed(const Duration(milliseconds: 1000));
    _mockExpenses.insert(0, expense);
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
}
