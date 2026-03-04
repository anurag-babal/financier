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

## 2. Go-based Microservices (e.g., Expense Service)
*   **Eureka Registration**: In `docker-compose.yml`, use `EUREKA_URL=http://service-registry:8761/eureka`.
*   **Port Management**: Microservices must dynamically register their internal listening port (e.g., `8082`) with Eureka.
*   **Headers**: Services like `expense-service` expect the `X-User-Id` header for row-level authorization.

## 3. Database Management
*   **Schema Sync**: If backend entity relationships change (e.g., the User-Profile normalization), perform a clean reset using `docker-compose down -v`.
*   **Normalization**: Personal user details (bio, currency, budget) are stored in `user_profiles` to separate them from core authentication data in the `users` table.
