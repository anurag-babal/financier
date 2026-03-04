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
The frontend is a Flutter application. UI standards and data patterns are maintained in the [Financier Frontend Guidelines skill](../financier_frontend/SKILL.md).

*   **Design**: Luxury Dark Mode with `GoogleFonts` (Outfit).
*   **Resiliency**: Independent service loading for multi-service screens.

## 3. General Guiding Principles
*   **Monorepo Strategy**: Keep mobile, backend, and infra in one repo for atomic commits.
*   **Clean Architecture**: Separation of UI and Data layers in Flutter.
*   **Schema Consistency**: Use `docker-compose down -v` after major entity changes.
