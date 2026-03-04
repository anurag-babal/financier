import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:financier_app/main.dart';

void main() {
  testWidgets('Financier app smoke test', (WidgetTester tester) async {
    // Build our app and trigger a frame.
    await tester.pumpWidget(const FinancierApp());

    // Verify that our app starts and shows the title.
    expect(find.text('Financier'), findsOneWidget);
    
    // Verify that the floating action button is present
    expect(find.byType(FloatingActionButton), findsOneWidget);
  });
}
