class AppConfig {
  // Use --dart-define=API_URL=https://api.yourdomain.com when building/running
  // Default is 10.0.2.2 for Android Emulator
  static const String _defaultUrl = 'http://10.0.2.2:8080';
  
  static const String baseUrl = String.fromEnvironment(
    'API_URL',
    defaultValue: _defaultUrl,
  );

  static bool get isProduction => !baseUrl.contains('10.0.2.2') && !baseUrl.contains('localhost');
}
