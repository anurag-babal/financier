import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import '../models/transaction_model.dart';
import '../models/user_model.dart';
import '../models/dashboard_summary_model.dart';
import '../../core/app_config.dart';
import 'mock_api_service.dart';
import 'auth_helper.dart';

class HttpApiService implements MockApiService {
  final String _baseUrl = AppConfig.baseUrl;
  final http.Client _client;
  
  static String? _token;

  HttpApiService({http.Client? client}) : _client = client ?? http.Client();

  // Initialize service by loading the saved token
  static Future<void> initialize() async {
    _token = await AuthHelper.getToken();
  }

  String get _authHeader => _token != null ? 'Bearer $_token' : '';

  @override
  Future<List<Transaction>> getTransactions() async {
    try {
      final response = await _client.get(
        Uri.parse('$_baseUrl/transaction-service/api/transactions'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': _authHeader,
        },
      );

      if (response.statusCode == 200) {
        final dynamic decodedData = json.decode(response.body);
        if (decodedData == null) return [];
        
        final List<dynamic> data = decodedData as List<dynamic>;
        return data.map((json) {
          // Map backend fields to frontend Transaction model
          // Note: Backend might use _id instead of id, ensure model is robust
          return Transaction(
            id: json['_id'] ?? json['id'] ?? '',
            userId: json['user_id'] ?? json['userId'] ?? '',
            type: json['type'] ?? 'EXPENSE',
            amount: (json['amount'] as num).toDouble(),
            category: json['category'] ?? '',
            description: json['description'] ?? '',
            date: DateTime.parse(json['date'] ?? DateTime.now().toIso8601String()),
          );
        }).toList();
      } else {
        throw Exception('Failed to load transactions: ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching transactions: $e');
      throw Exception('Failed to connect to backend: $e');
    }
  }

  @override
  Future<DashboardSummary> getDashboardSummary() async {
    try {
      final response = await _client.get(
        Uri.parse('$_baseUrl/transaction-service/api/transactions/summary'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': _authHeader,
        },
      );

      if (response.statusCode == 200) {
        return DashboardSummary.fromJson(json.decode(response.body));
      } else {
        throw Exception('Failed to load dashboard summary: ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching dashboard summary: $e');
      throw Exception('Failed to connect to backend: $e');
    }
  }

  @override
  Future<List<Transaction>> getRecentTransactions() async {
    try {
      final response = await _client.get(
        Uri.parse('$_baseUrl/transaction-service/api/transactions/recent?limit=5'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': _authHeader,
        },
      );

      if (response.statusCode == 200) {
        final dynamic decodedData = json.decode(response.body);
        if (decodedData == null) return [];
        
        final List<dynamic> data = decodedData as List<dynamic>;
        return data.map((json) {
          return Transaction(
            id: json['_id'] ?? json['id'] ?? '',
            userId: json['user_id'] ?? json['userId'] ?? '',
            type: json['type'] ?? 'EXPENSE',
            amount: (json['amount'] as num).toDouble(),
            category: json['category'] ?? '',
            description: json['description'] ?? '',
            date: DateTime.parse(json['date'] ?? DateTime.now().toIso8601String()),
          );
        }).toList();
      } else {
        throw Exception('Failed to load recent transactions: ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching recent transactions: $e');
      throw Exception('Failed to connect to backend: $e');
    }
  }

  @override
  Future<User> getUserProfile() async {
    try {
      final response = await _client.get(
        Uri.parse('$_baseUrl/user-service/api/v1/users/me'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': _authHeader,
        },
      );

      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        return User(
          id: data['id'] is int ? data['id'] : int.tryParse(data['id'].toString()) ?? 0,
          name: data['name'] ?? data['username'] ?? 'User',
          username: data['username'] ?? 'user',
          email: data['email'] ?? '',
          profilePictureUrl: data['profilePictureUrl'] ?? 'https://i.pravatar.cc/150',
          bio: data['bio'] ?? '',
          currency: data['currency'] ?? 'INR',
          monthlyBudget: data['monthlyBudget'] != null ? (data['monthlyBudget'] as num).toDouble() : null,
        );
      } else {
        throw Exception('Failed to load user profile: ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching user: $e');
      throw Exception('Failed to connect to backend: $e');
    }
  }

  @override
  Future<void> addExpense(Expense expense) async {
    try {
      final response = await _client.post(
        Uri.parse('$_baseUrl/transaction-service/api/expenses'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': _authHeader,
        },
        body: json.encode({
          'type': expense.type,
          'amount': expense.amount,
          'category': expense.category,
          'description': expense.description,
          'date': expense.date.toUtc().toIso8601String(),
        }),
      );

      if (response.statusCode != 200 && response.statusCode != 201) {
        throw Exception('Failed to add expense: ${response.statusCode}');
      }
    } catch (e) {
      print('Error adding expense: $e');
      throw Exception('Failed to connect to backend: $e');
    }
  }
  @override
  Future<void> register(String name, String email, String password, String currency) async {
    try {
      final response = await _client.post(
        Uri.parse('$_baseUrl/user-service/api/v1/users/register'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          'name': name,
          'email': email,
          'password': password,
          'currency': currency,
        }),
      );

      if (response.statusCode != 200 && response.statusCode != 201) {
        throw Exception('Registration failed: ${response.body}');
      }
    } catch (e) {
      print('Error during registration: $e');
      throw Exception('Failed to connect to backend: $e');
    }
  }

  @override
  Future<String> login(String email, String password) async {
    try {
      print('$_baseUrl/user-service/api/v1/users/login');
      final response = await _client.post(
        Uri.parse('$_baseUrl/user-service/api/v1/users/login'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          'email': email,
          'password': password,
        }),
      );

      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        final token = data['token'] ?? '';
        _token = token; // Store token in memory
        await AuthHelper.saveToken(token); // Store token persistently
        return token;
      } else {
        throw Exception('Login failed: ${response.statusCode}');
      }
    } catch (e) {
      print('Error during login: $e');
      throw Exception('Failed to connect to backend: $e');
    }
  }

  @override
  Future<void> updateProfile(User user) async {
    try {
      final response = await _client.put(
        Uri.parse('$_baseUrl/user-service/api/v1/users/me'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': _authHeader, // In real app, we'd use the stored token
        },
        body: json.encode({
          'name': user.name,
          'bio': user.bio,
          'profilePictureUrl': user.profilePictureUrl,
          'currency': user.currency,
          'monthlyBudget': user.monthlyBudget,
        }),
      );

      if (response.statusCode != 200) {
        throw Exception('Update profile failed: ${response.statusCode}');
      }
    } catch (e) {
      print('Error updating profile: $e');
      throw Exception('Failed to connect to backend: $e');
    }
  }
}
