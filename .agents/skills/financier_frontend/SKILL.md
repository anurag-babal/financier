---
name: Financier Frontend Guidelines
description: UI/UX standards, typography, and data resiliency patterns for the Financier Flutter application.
---

# Financier Frontend Guidelines

## 1. Typography and Symbols
*   **GoogleFonts**: Always use `GoogleFonts` (e.g., `GoogleFonts.outfitTextTheme`) instead of standard system fonts in the `AppTheme`. This ensures that native currency symbols like the Rupee symbol (**₹**) are rendered correctly across all Android versions/emulators.

## 2. Data Resiliency Patterns
*   **Independent Service Loading**: In screens that fetch data from multiple microservices (like `DashboardScreen`), use independent `try-catch` blocks for each API call. 
*   **Empty Response Handling (Null-Safety)**: When fetching lists (e.g., expenses), always check if the decoded JSON is `null`. Go backends may return `null` instead of `[]` for empty slices. Implement a check: `if (decodedData == null) return [];`.
*   **Graceful Fallbacks**: Ensure the UI can still display partial information (like the user's name) even if a secondary service (like expenses) is temporarily unreachable.

## 3. Authentication & Persistence
*   **Persistent Sessions**: Use `shared_preferences` to store the JWT token locally. 
*   **AuthHelper Pattern**: Centralize token management (save, get, logout) in a dedicated `AuthHelper` class.
*   **Automatic Boot-Routing**: Use a `FutureBuilder` in `main.dart` to check `AuthHelper.isLoggedIn()` on app startup. Route directly to `DashboardScreen` if authenticated, bypassing the login screen.
*   **Token initialization**: Ensure `HttpApiService` is initialized with the stored token immediately upon app start so subsequent requests are authenticated.

## 4. Theming & UX
*   **Luxury Dark Mode**: Strictly follow `AppColors` in `lib/core/theme.dart`.
*   **Animations**: Maintain the premium feel by wrapping new UI elements in `animate_do` widgets (`FadeInUp`, etc.).
*   **Currency Mapping**: Use the `currencySymbol` getter in `UserModel` to map currency codes to their native glyphs.
*   **Color-Coded Feedback**: Use meaningful semantic colors (e.g., Green for income, Red for expenses) to provide instant visual feedback on transaction types.

## 5. Testing Guidelines

### Dependency Injection for Services
*   **Injectable Clients**: Refactor singleton-style services (like `HttpApiService`) to accept an optional `http.Client` in the constructor. This allows for mocking the network layer using `http/testing`'s `MockClient` without making real network calls.

### Mocking Platform Channels
*   **SharedPreferences**: When testing classes that use `AuthHelper` or `SharedPreferences`, always initialize the mock plugin values in the `setUp` block:
    ```dart
    setUp(() {
      SharedPreferences.setMockInitialValues({});
    });
    ```
*   **Testing State**: Use `package:flutter_test` and `Group` blocks to organize service logic tests separately from UI widget tests.
