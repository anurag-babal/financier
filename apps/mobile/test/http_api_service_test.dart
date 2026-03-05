import 'dart:convert';
import 'package:flutter_test/flutter_test.dart';
import 'package:http/http.dart' as http;
import 'package:http/testing.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:financier_app/data/services/http_api_service.dart';
import 'package:financier_app/data/models/expense_model.dart';

void main() {
  setUp(() {
    SharedPreferences.setMockInitialValues({});
  });

  group('HttpApiService Tests', () {
    test('getExpenses returns a list of expenses on 200', () async {
      final mockClient = MockClient((request) async {
        return http.Response(
          json.encode([
            {
              'id': '1',
              'user_id': 'user1',
              'amount': 50.0,
              'category': 'Food',
              'description': 'Lunch',
              'date': DateTime.now().toIso8601String(),
              'type': 'EXPENSE'
            }
          ]),
          200,
        );
      });

      final service = HttpApiService(client: mockClient);
      final expenses = await service.getExpenses();

      expect(expenses, isA<List<Expense>>());
      expect(expenses.length, 1);
      expect(expenses[0].amount, 50.0);
    });

    test('login returns token on 200', () async {
      final mockClient = MockClient((request) async {
        return http.Response(
          json.encode({'token': 'mock-token'}),
          200,
        );
      });

      final service = HttpApiService(client: mockClient);
      final token = await service.login('test@example.com', 'password');

      expect(token, 'mock-token');
    });

    test('throws exception on non-200 response', () async {
      final mockClient = MockClient((request) async {
        return http.Response('Error', 404);
      });

      final service = HttpApiService(client: mockClient);
      
      expect(service.getExpenses(), throwsException);
    });
    group('POST and PUT requests', () {
      test('addExpense sends correct data', () async {
        bool called = false;
        final mockClient = MockClient((request) async {
          called = true;
          expect(request.method, 'POST');
          expect(request.url.path, contains('api/expenses'));
          return http.Response('', 201);
        });

        final service = HttpApiService(client: mockClient);
        await service.addExpense(Expense(
          id: '',
          userId: '',
          amount: 10,
          category: 'test',
          description: 'test',
          date: DateTime.now(),
          type: 'EXPENSE',
        ));

        expect(called, true);
      });
    });
  });
}
