# AI Retail Suite

Production-ready backend for an AI-powered **Point of Sale (POS)** and **Inventory Management System**.

## Technology Stack

| Layer | Technology |
|-------|------------|
| Runtime | Java 21 |
| Framework | Spring Boot 3.4 |
| Build | Gradle (Groovy DSL) |
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

- Java 21+ for the application (Gradle toolchain auto-provisions JDK 21 if needed)
- **Gradle 9.6+** to sync/build with **Java 25** as the Gradle JVM (IntelliJ: **Settings → Build Tools → Gradle → Gradle JVM**)
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
./gradlew bootRun --args='--spring.profiles.active=local'
```

Or build and run the JAR:

```bash
./gradlew bootJar
java -jar build/libs/ai-retail-suite.jar --spring.profiles.active=local
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
./gradlew test
```

Test coverage includes:
- Unit tests: `AuthServiceTest`, `CompanyServiceTest`, `JwtTokenProviderTest`
- Integration tests: `CompanyControllerIntegrationTest` (Testcontainers PostgreSQL)

## IntelliJ IDEA

Shared run configurations are in `.run/` and appear in the run dropdown after opening the project.

### One-time setup

1. **File → Open** → select the project folder (or `build.gradle`).
2. Import as a **Gradle** project.
3. Set **Project SDK** to **Java 21** (**File → Project Structure → Project**).
4. Set **Gradle JVM** to **Java 25** (or Java 17/21/23) — **Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JVM**. Use **Java 25** only with Gradle **9.6+** (included in this project).
5. Enable annotation processing: **Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing**.
6. Install plugins if needed: **Spring Boot**, **Docker** (for Docker run configs).

### Run / Debug configurations

| Configuration | Use case |
|---------------|----------|
| **AiRetailSuite (local)** | Daily dev — `local` profile, DB at `localhost:5432` |
| **AiRetailSuite (dev)** | `dev` profile with `DB_*` environment variables |
| **Docker Postgres** | Start only PostgreSQL via Docker Compose |
| **Docker Compose (full stack)** | Start app + PostgreSQL in Docker |
| **Gradle Test** | Run all tests |

### Typical local workflow

1. Run **Docker Postgres** (or `docker compose up postgres -d`).
2. **Run** or **Debug** **AiRetailSuite (local)** from the toolbar.
3. Open http://localhost:8080/api/swagger-ui.html

Use the **Debug** button (or `⌃D` / `Ctrl+D`) to hit breakpoints in services and controllers.

If **Module** is unresolved after import, edit the run config and set it to `ai-retail-suite.main`.

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
build.gradle
settings.gradle
gradle.properties
gradlew
Dockerfile
docker-compose.yml
```

## Common Gradle Commands

| Command | Description |
|---------|-------------|
| `./gradlew bootRun` | Run the application |
| `./gradlew bootRun --args='--spring.profiles.active=local'` | Run with `local` profile |
| `./gradlew bootJar` | Build executable JAR |
| `./gradlew test` | Run tests |
| `./gradlew clean build` | Clean and full build |
| `./gradlew dependencies` | Show dependency tree |

## License

Proprietary — NextStack Lab
