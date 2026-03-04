import 'package:flutter/material.dart';
import 'core/theme.dart';
import 'presentation/screens/login_screen.dart';
import 'presentation/screens/dashboard_screen.dart';
import 'data/services/auth_helper.dart';
import 'data/services/http_api_service.dart';

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
      home: FutureBuilder<bool>(
        future: AuthHelper.isLoggedIn(),
        builder: (context, snapshot) {
          // While checking for the token, show a loading indicator
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Scaffold(
              backgroundColor: AppColors.background,
              body: Center(child: CircularProgressIndicator(color: AppColors.primary)),
            );
          }
          
          // If a token exists, the user is logged in
          final isLoggedIn = snapshot.data ?? false;
          
          if (isLoggedIn) {
            // Pre-initialize HttpApiService with the token
            HttpApiService.initialize();
            return const DashboardScreen();
          } else {
            return const LoginScreen();
          }
        },
      ),
    );
  }
}
