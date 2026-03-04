import 'package:flutter/material.dart';
import 'core/theme.dart';
import 'presentation/screens/login_screen.dart';
import 'presentation/screens/dashboard_screen.dart';

void main() {
  runApp(const FinancierApp());
}

class FinancierApp extends StatelessWidget {
  const FinancierApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Financier',
      debugShowCheckedModeBanner: false,
      theme: AppTheme.darkTheme,
      home: const LoginScreen(),
    );
  }
}
