# AI Retail Suite

Production-ready backend for an AI-powered **Point of Sale (POS)** and **Inventory Management System**.

## Technology Stack

| Layer | Technology |
|-------|------------|
| Runtime | Java 21 |
| Framework | Spring Boot 3.4 |
| Build | Maven |
| Database | PostgreSQL 16 |
| ORM | Spring Data JPA (Hibernate) |
| Security | Spring Security + JWT |
| Migrations | Flyway |
| Mapping | MapStruct |
| API Docs | Springdoc OpenAPI (Swagger) |
| Testing | JUnit 5, Mockito, Testcontainers |
| Containerization | Docker, Docker Compose |

## Architecture

Clean / layered architecture with domain modules:

```
com.nextstack.airetail
├──  config          # JPA auditing, OpenAPI, mail, properties
 ├──  security        # JWT filter, SecurityConfig, UserPrincipal
 ├──  auth            # Login, logout, refresh, forgot/reset password
 ├──  common          # BaseEntity, ApiResponse, pagination, specifications
 ├──  company / branch / user / role / permission
 ├──  product / category / brand / inventory
 ├──  customer / sales / payment
 ├──  report / audit / exception / util
```

Each module contains: `controller`, `service`, `service/impl`, `repository`, `entity`, `dto`, `mapper`, `specification`, `validator`.

## Quick Start

### Prerequisites

- Java 21+
- Maven 3.9+ (or use `./mvnw`)
- Docker & Docker Compose (optional)

### Run with Docker Compose

```bash
docker compose up --build
```

Services:
- **API**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **PostgreSQL**: localhost:5432

### Run locally

1. Start PostgreSQL:

```bash
docker compose up postgres -d
```

2. Run the application:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

## Default Credentials

After Flyway migrations, a demo admin user is seeded:

| Field | Value |
|-------|-------|
| Username | `admin` |
| Password | `password` |
| Company | Demo Retail Co |

## API Overview

Base URL: `http://localhost:8080/api`

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/v1/auth/login` | Login and receive JWT tokens |
| POST | `/v1/auth/refresh` | Refresh access token |
| POST | `/v1/auth/logout` | Invalidate refresh token |
| POST | `/v1/auth/forgot-password` | Request password reset email |
| POST | `/v1/auth/reset-password` | Reset password with token |

### Domain Resources

| Resource | Base Path |
|----------|-----------|
| Companies | `/v1/companies` |
| Branches | `/v1/branches` |
| Users | `/v1/users` |
| Roles | `/v1/roles` |
| Permissions | `/v1/permissions` |
| Categories | `/v1/categories` |
| Brands | `/v1/brands` |
| Products | `/v1/products` |
| Inventory | `/v1/inventory` |
| Customers | `/v1/customers` |
| Sales | `/v1/sales` |
| Payments | `/v1/payments` |
| Reports | `/v1/reports` |
| Audit Logs | `/v1/audit-logs` |

### Standard Response Format

```json
{
  "success": true,
  "message": "Operation completed successfully.",
  "data": {},
  "timestamp": "2026-06-29T12:00:00Z",
  "path": "/api/v1/companies"
}
```

## Configuration Profiles

| Profile | Purpose |
|---------|---------|
| `local` | Local development (default) |
| `dev` | Docker / shared dev environment |
| `test` | Integration tests with Testcontainers |
| `prod` | Production deployment |

Key environment variables (prod):

```bash
DB_HOST, DB_PORT, DB_NAME, DB_USERNAME, DB_PASSWORD
JWT_SECRET
CORS_ALLOWED_ORIGINS
MAIL_FROM, RESET_PASSWORD_URL
```

## Security

- Stateless JWT authentication with refresh tokens
- BCrypt password encryption
- Role-based access control via permission codes (`COMPANY_READ`, `SALE_WRITE`, etc.)
- CORS configuration per profile
- Soft delete via `active` flag on all entities

## Testing

```bash
./mvnw test
```

Test coverage includes:
- Unit tests: `AuthServiceTest`, `CompanyServiceTest`, `JwtTokenProviderTest`
- Integration tests: `CompanyControllerIntegrationTest` (Testcontainers PostgreSQL)

## Database Migrations

Flyway migrations in `src/main/resources/db/migration/`:

- `V1__initial_schema.sql` — Full schema
- `V2__seed_permissions.sql` — Default permissions
- `V3__seed_demo_data.sql` — Demo company, branch, admin user

## Project Structure

```
src/main/java/com/nextstack/airetail/
src/main/resources/
  application.properties
  application-{local,dev,test,prod}.properties
  db/migration/
src/test/java/
Dockerfile
docker-compose.yml
pom.xml
```

## License

Proprietary — NextStack Lab
