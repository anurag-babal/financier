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

## 3. Theming
*   **Luxury Dark Mode**: Strictly follow `AppColors` in `lib/core/theme.dart`.
*   **Animations**: Maintain the premium feel by wrapping new UI elements in `animate_do` widgets (`FadeInUp`, etc.).
*   **Currency Mapping**: Use the `currencySymbol` getter in `UserModel` to map currency codes to their native glyphs.
