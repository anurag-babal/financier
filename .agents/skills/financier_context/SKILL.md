---
name: Financier Project Context
description: Guidelines and context for working on the Financier microservices and Flutter mobile application.
---

# Financier Project Context and Guidelines

The Financier project is a personal finance management tool used to track daily expenses, manage budgets, and display recent transactions. It consists of a microservice-based backend and a Flutter mobile application.

## 1. Backend Architecture

The backend is located in the `/services/` directory and is broken down into several microservices:

*   **`expense-service`**: 
    *   **Stack**: Go (Golang), Gin web framework, MongoDB.
    *   **Architecture**: Uses a handler-repository pattern (`handler/`, `repository/`, `model/`).
    *   **Key Models**: `Expense` (fields like `Amount`, `Category`, `Description`, `Date`, etc.). Uses `primitive.ObjectID`.
*   **`user-service`**:
    *   **Stack**: Java (likely Spring Boot based on `pom.xml` and `mvnw`).
    *   **Key Models**: `User` (fields like `email`, `username`, `profilePictureUrl`, etc.). Uses `Long` for IDs.
*   **Other Services**: `budget-service`, `notification-service`, `reporting-service` (these exist but are currently uninspected).

### Infrastructure Component
The project utilizes a microservices infrastructure layer in the `/infrastructure/` directory:
*   **`api-gateway`**: The single entry point for mobile/web apps, routing requests to appropriate background services. **Migrated to Reactive Spring Cloud Gateway (WebFlux)**.
*   **`service-registry`**: (Netflix Eureka) Service discovery registry.
*   **`config-server`**: Centralized configuration management.

*Architecture Assessment*: This provides high scalability, polyglot capabilities (Go/Java), and fault isolation, at the cost of operational complexity (networking, data consistency, deployment overhead).

## 2. Frontend Application Context

The frontend application is located in `/frontend/financier-app/` and is built using **Flutter** for Web, Android, and iOS.

*   **Architecture Pattern**: Clean Architecture inspired.
    *   `lib/core/`: Application-wide constants, themes, and configurations.
    *   `lib/data/`: Models (e.g., `expense_model.dart`, `user_model.dart`) and services (`http_api_service.dart`).
    *   `lib/domain/`: (Reserved for business logic / repository interfaces in the future).
    *   `lib/presentation/`: UI components, screens (`login_screen.dart`, `register_screen.dart`, `onboarding_screen.dart`, `dashboard_screen.dart`).
*   **User Lifecycle**:
    1.  **Registration**: Phase 1 (Name, Email, Password, Currency).
    2.  **Onboarding**: Phase 2 (Bio, Monthly Budget) triggered if profile is incomplete.
    3.  **Dashboard**: Personalized with currency and budget goals.
*   **Design Aesthetics**: Premium, Luxury Dark Mode.
    *   **Color Palette**: Deep Navy/Slate backgrounds (`0xFF0F172A`, `0xFF1E293B`), with vibrant accents like Emerald Green (`0xFF10B981`) for primary actions/income and Rose/Coral (`0xFFF43F5E`) for expenses.
    *   **Style**: Uses glassmorphism effects (subtle borders with gradient backgrounds) and clean typography (`Outfit` font family or similar).
*   **Key Libraries & Dependencies**:
    *   `animate_do`: Used for smooth entry animations (e.g., `FadeInUp`, `FadeInDown`). All UI updates should feel smooth.
    *   `fl_chart`: Used for rendering detailed analytics and spending trend charts in the dashboard.
    *   `intl`: Used for formatting dates and currencies.
    *   `http`: Used for network requests to the backend.
*   **Current State**: The application uses `HttpApiService` to connect to the backend API Gateway (typically running at `10.0.2.2:8080` for Android emulators). It connects to `expense-service` and `user-service` to fetch real data.

## 3. Guiding Principles for AI Assistants

*   **UI Quality**: Do not generate basic or dull UI for the Flutter application. Always adhere strictly to the established `AppColors` and `AppTheme` within the `lib/core/theme.dart` file. Ensure newly created widgets use animations to match the existing feel.
*   **Code Structure**: When adding features to the mobile app, maintain separation of concerns. Do not mix data fetching directly into presentation UI logic.
*   **API Integration**: The `HttpApiService` dynamically switches base URLs between the Android Emulator (`10.0.2.2:8080`) and the Web/Browser (`localhost:8080`) based on the platform.
*   **Emulator and Web Testing**: The application is verified to run on Android emulators and as a Web app via `flutter run -d chrome`. A `Dockerfile` is provided for production deployment of the web version using Nginx.

## 4. Repository Strategy

**Recommendation: Monorepo**
Given the current structure with a root `docker-compose.yml`, a Single Repository (Monorepo) is recommended.
*   **Simplicity**: Commit changes across the mobile app, backend services, and infrastructure in a single unified commit.
*   **Testing**: Easy for developers to clone one repository and run `docker-compose up` to run the entire backend system.
*   **Maintenance**: If an API contract changes, both the backend (Go/Java) and frontend (Flutter) code updates can be tracked together.

## 5. Current State & Known Issues

*   **API Gateway & Web-App Integration**: The `HttpApiService` successfully integrates with the API Gateway. The web-app frontend registration flow is fully functional after resolving several infrastructure blocks.
*   **Learnings from WebFlux Migration & Registration Fix**:
    *   **Gateway Type**: The `api-gateway` has been successfully migrated to **Reactive Spring Cloud Gateway (WebFlux)**.
    *   **CORS Resolution**: CORS is handled globally at the Gateway level using reactive properties (`spring.cloud.gateway.globalcors`). Downstream services (like `user-service`) should have their local CORS configurations disabled to avoid conflicts.
    *   **Auth Policy**: `user-service` now correctly handles registration and login endpoints as public using `AntPathRequestMatcher` in Spring Security.
    *   **Database Synchronization**: The `user-db` schema for the `users` table was updated to include auditing columns (`created_at`, `updated_at`) and the DTO was adjusted to handle the `name` field from the frontend, ensuring compatibility between the UI and the persistence layer.
    *   **Fallback Logic**: The `user-service` now provides a `username` fallback (using email) to satisfy database constraints while allowing users to register with just a display name.
