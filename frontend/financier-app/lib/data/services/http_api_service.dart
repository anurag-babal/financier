import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import '../models/expense_model.dart';
import '../models/user_model.dart';
import 'mock_api_service.dart';

class HttpApiService implements MockApiService {
  // Use 10.0.2.2 for Android Emulator, localhost for Web/Browser
  final String _baseUrl = kIsWeb ? 'http://localhost:8080' : 'http://10.0.2.2:8080';
  
  // A temporary token/user identifier to simulate auth until full login is built
  final String _authHeader = 'Bearer temp-token';

  @override
  Future<List<Expense>> getExpenses() async {
    try {
      final response = await http.get(
        Uri.parse('$_baseUrl/expense-service/api/expenses'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': _authHeader,
        },
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body);
        return data.map((json) {
          // Map backend fields to frontend Expense model
          // Note: Backend might use _id instead of id, ensure model is robust
          return Expense(
            id: json['_id'] ?? json['id'] ?? '',
            userId: json['user_id'] ?? json['userId'] ?? '',
            amount: (json['amount'] as num).toDouble(),
            category: json['category'] ?? '',
            description: json['description'] ?? '',
            date: DateTime.parse(json['date'] ?? DateTime.now().toIso8601String()),
          );
        }).toList();
      } else {
        throw Exception('Failed to load expenses: ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching expenses: $e');
      throw Exception('Failed to connect to backend: $e');
    }
  }

  @override
  Future<User> getUserProfile() async {
    try {
      final response = await http.get(
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
          currency: data['currency'] ?? 'USD',
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
      final response = await http.post(
        Uri.parse('$_baseUrl/expense-service/api/expenses'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': _authHeader,
        },
        body: json.encode({
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
      final response = await http.post(
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
      final response = await http.post(
        Uri.parse('$_baseUrl/user-service/api/v1/users/login'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          'email': email,
          'password': password,
        }),
      );

      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        return data['token'] ?? ''; // AuthResponse contains token field
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
      final response = await http.put(
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
