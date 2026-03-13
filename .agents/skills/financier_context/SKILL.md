---
name: Financier Project Context
description: Guidelines and context for working on the Financier microservices and Flutter mobile application.
---

# Financier Project Context and Guidelines

The Financier project is a personal finance management tool used to track daily expenses, manage budgets, and display recent transactions. It consists of a microservice-based backend and a Flutter mobile application.

## 1. Backend Architecture

The backend is located in the `/services/` directory and is broken down into several microservices:

*   **`transaction-service`**: 
    *   **Stack**: Java Spring Boot 3.5, Hexagonal Architecture.
    *   **Key Models**: `Transaction` (Unified Ledger for Income/Expense).
    *   **Architecture**: Uses `domain`, `application`, `interface_`, and `data` packages.
*   **`user-service`**:
    *   **Stack**: Java Spring Boot, Hexagonal Architecture.
    *   **Role**: Identity management and user profiles.
*   **Other Services**: `budget-service`, `notification-service`, `reporting-service`.

### Infrastructure Component
The project utilizes a microservices infrastructure layer in the `/infrastructure/` directory:
*   **`api-gateway`**: The single entry point for mobile/web apps, routing requests to appropriate background services. **Migrated to Reactive Spring Cloud Gateway (WebFlux)**.
*   **`service-registry`**: (Netflix Eureka) Service discovery registry.
*   **`config-server`**: Centralized configuration management.

*Architecture Assessment*: This provides high scalability, polyglot capabilities (Go/Java), and fault isolation, at the cost of operational complexity (networking, data consistency, deployment overhead).

## 2. Directory Structure (Monorepo)
*   **/apps/mobile**: The Flutter application (Android/iOS).
*   **/services**: Backend microservices (Go/Java).
*   **/infrastructure**: Shared infrastructure (Eureka, API Gateway, RabbitMQ).
*   **/docs**: Project documentation.

## 3. Environment Management
*   **Centralized Configuration**: All environment variables (ports, database credentials, secrets) are managed in a root-level `.env` file (not checked into Git). 
*   **Env Template**: Refer to `.env.example` for the required keys.
*   **Docker Injection**: The `docker-compose.yml` serves as a template that injects variables from `.env` into service containers.

## 4. Frontend Application Context
The mobile app (Flutter) is located in `apps/mobile/`. UI standards and data patterns are maintained in the [Financier Frontend Guidelines skill](../financier_frontend/SKILL.md).

*   **Design**: Luxury Dark Mode with `GoogleFonts` (Outfit).
*   **Resiliency**: Independent service loading for multi-service screens.
*   **Authentication**: JWT-based session persistence using `shared_preferences`.
*   **Dynamic API URL**: Use the `AppConfig` class in `lib/core/app_config.dart`. It leverages `--dart-define` to allow switching base URLs without code changes.
*   **Symlink Support**: Building on Windows with native plugins (like `shared_preferences`) requires **Windows Developer Mode** enabled.

## 3. General Guiding Principles
*   **Monorepo Strategy**: Keep mobile, backend, and infra in one repo for atomic commits.
*   **Clean Architecture**: Separation of UI and Data layers in Flutter.
*   **Schema Consistency**: Use `docker-compose down -v` after major entity changes.
