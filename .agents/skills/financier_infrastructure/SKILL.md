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

## 2. Go-based Microservices
*   **`expense-service`**: 
    *   **Stack**: Go (Golang), Gin web framework, PostgreSQL (Migrated from MongoDB).
*   **Eureka Registration**: In `docker-compose.yml`, use `EUREKA_URL=http://service-registry:8761/eureka`.
*   **Port Management**: Microservices must dynamically register their internal listening port (e.g., `8082`) with Eureka.
*   **Headers**: Services like `expense-service` expect the `X-User-Id` header for row-level authorization.

## 3. Database Management
*   **Schema Sync**: If backend entity relationships change (e.g., the User-Profile normalization), perform a clean reset using `docker-compose down -v`.
*   **Normalization**: Personal user details (bio, currency, budget) are stored in `user_profiles` to separate them from core authentication data in the `users` table.
*   **Unified Ledger Pattern**: Instead of separate collections for Incomes and Expenses, use a single `transactions` collection with a `type` field ("INCOME" or "EXPENSE"). This simplifies total balance calculations and "Recent Transactions" queries.
*   **MongoDB Aggregations**: Use database-level aggregations (`$group`, `$match`, `$sum`) to provide a pre-calculated `/summary` endpoint. This is more efficient for mobile clients than fetching raw data and calculating on the device.

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
