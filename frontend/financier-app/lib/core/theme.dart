import 'package:flutter/material.dart';

class AppColors {
  static const Color background = Color(0xFF0F172A); // Deep Navy
  static const Color surface = Color(0xFF1E293B);    // Slate
  static const Color primary = Color(0xFF10B981);   // Emerald Green
  static const Color secondary = Color(0xFFF43F5E); // Rose/Coral
  static const Color textBody = Color(0xFFF8FAFC);
  static const Color textMuted = Color(0xFF94A3B8);
  static const Color accent = Color(0xFF8B5CF6);    // Violet
}

class AppTheme {
  static ThemeData get darkTheme {
    return ThemeData(
      brightness: Brightness.dark,
      scaffoldBackgroundColor: AppColors.background,
      primaryColor: AppColors.primary,
      colorScheme: const ColorScheme.dark(
        primary: AppColors.primary,
        secondary: AppColors.secondary,
        surface: AppColors.surface,
      ),
      fontFamily: 'Outfit',
      appBarTheme: const AppBarTheme(
        backgroundColor: Colors.transparent,
        elevation: 0,
        centerTitle: false,
        titleTextStyle: TextStyle(
          fontSize: 24,
          fontWeight: FontWeight.bold,
          color: AppColors.textBody,
        ),
      ),
      cardTheme: CardThemeData(
        color: AppColors.surface,
        elevation: 0,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(16),
          side: BorderSide(color: Colors.white.withOpacity(0.1)),
        ),
      ),
    );
  }
}
