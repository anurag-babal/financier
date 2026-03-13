---
name: Financier Backend Infrastructure
description: Specific technical details for the Financier microservices, Eureka registration, and API Gateway routing.
---

# Financier Backend Infrastructure Guidelines

## 1. API Gateway (Spring Cloud Gateway)
*   **Routing**: Every new service must be explicitly added to `infrastructure/api-gateway/src/main/resources/application.properties`.
*   **Path Mapping**: Use `Path=/service-name/**` and `StripPrefix=1` to ensure correct forwarding.
*   **Authentication**: The Gateway is the entry point for JWT validation. 
    *   **JWT Filter**: Implemented a `GlobalFilter` (`JwtAuthenticationFilter.java`) to parse tokens.
    *   **User ID Extraction**: **CRITICAL**: Use `String.valueOf(claims.get("id"))` to extract the ID. Claims may be returned as `Integer` or `Long` depending on the token provider; casting to a specific type can cause `ClassCastException`.
    *   **Header Injection**: Injects the `X-User-Id` header for down-stream microservices.

## 2. Microservices Architecture
*   **Hexagonal Architecture (Ports and Adapters)**: Both `user-service` and `transaction-service` follow this pattern to decouple business logic from infrastructure.
    *   **Domain Layer**: Contains core POJO models and repository interfaces (Ports). No framework-specific annotations (like JPA) should be in this layer.
    *   **Application Layer**: Orchestrates use cases. Contains service interfaces (Ports), their implementations, and DTOs.
    *   **Interface Layer**: Driving Adapters (e.g., REST Controllers).
    *   **Data Layer**: Driven Adapters (e.g., JPA Entities, Persistence Adapters, Mappers).
*   **`user-service`**: Java Spring Boot, PostgreSQL. Handles auth and user management.
*   **`transaction-service`**: Java Spring Boot 3.5, Hibernate/JPA, PostgreSQL.
    *   **Unified Ledger Pattern**: Instead of separate tables for Incomes and Expenses, a single `transactions` table with a `type` field ("INCOME" or "EXPENSE") is used.
*   **Eureka Registration**: Use `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` for service discovery configuration.

## 3. Database Management
*   **Schema Sync**: If backend entity relationships change (e.g., the User-Profile normalization), perform a clean reset using `docker-compose down -v`.
*   **Normalization**: Personal user details (bio, currency, budget) are stored in `user_profiles` to separate them from core authentication data in the `users` table.
*   **Unified Ledger Pattern**: Instead of separate tables/collections for Incomes and Expenses, use a single `transactions` table with a `type` field ("INCOME" or "EXPENSE"). This simplifies total balance calculations and "Recent Transactions" queries.
*   **Aggregations**: Use database-level aggregations or stream-based processing in the service layer (as done in `transaction-service`) to provide a pre-calculated `/summary` endpoint.

## 4. Testing Guidelines

### Go (GORM + SQLMock)
*   **Query Matching**: GORM generates specific SQL strings for Postgres (e.g., using `RETURNING "id"` and double quotes for table/column names). Use `regexp.QuoteMeta` to ensure `sqlmock` matches these exactly.
*   **Argument Order**: GORM often emits arguments in a specific order (sometimes alphabetical, sometimes by struct definition). Always check the "expectation not matched" error output to align `WithArgs` correctly.
*   **Unit vs Integration**: Prefer handler-level unit tests with full repository mocks over complex `sqlmock` repository tests when the SQL logic becomes brittle due to GORM's internal query building.

### Java (Spring Boot 3.4+)
*   **Mockito Strategy**: Use `@MockitoBean` (the modern replacement for `@MockBean`) for injecting mocks into the Spring ApplicationContext during `@WebMvcTest`.
*   **Security Filter Passthrough**: When testing controllers protected by a custom `JwtAuthFilter`, you MUST configure the mock to pass through the filter chain in a `@BeforeEach` block:
    ```java
    doAnswer(invocation -> {
        FilterChain filterChain = invocation.getArgument(2);
        filterChain.doFilter(invocation.getArgument(0), invocation.getArgument(1));
        return null;
    }).when(jwtAuthFilter).doFilter(any(), any(), any());
    ```
*   **DTO Verification**: Ensure DTOs (like `LoginRequest`) have both default constructors and setters, as Spring's JSON binding and standard test instantiations depend on them.
