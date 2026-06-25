# Feature Flag Service

A simple REST API for managing feature flags, built with Java and Spring Boot. This service allows platform teams to dynamically turn functionality on or off without redeploying.

## Prerequisites
* **Java 21** or newer
* **Maven** (included via wrapper)

## How to Build and Run

To build and run the application locally, execute the following command in the root directory:


```
.\mvnw spring-boot:run
(On Windows, use mvnw.cmd spring-boot:run)
```
The service will start on http://localhost:8080.

API Usage (cURL Examples)
Note: The following examples use standard Bash syntax. If you are using Windows PowerShell, consider using Invoke-RestMethod, Postman, or run these commands in Git Bash / WSL.

1. Create a flag
```
curl -X POST http://localhost:8080/flags \
-H "Content-Type: application/json" \
-d '{"name": "new-checkout-flow", "description": "Enables the new checkout UI", "enabled": false}'
```
2. List all flags
```
curl http://localhost:8080/flags
```
3. Get a specific flag by ID
```
curl http://localhost:8080/flags/1
```
4. Update a flag (PATCH)
```
curl -X PATCH http://localhost:8080/flags/1 \
-H "Content-Type: application/json" \
-d '{"enabled": true}'
```
5. Evaluate a flag by name
```
curl http://localhost:8080/flags/new-checkout-flow/evaluate
```
6. Delete a flag
```
curl -X DELETE http://localhost:8080/flags/1
```
### Key Design Decisions & Trade-offs
Architecture: Used a standard 3-tier architecture (Controller, Service, Repository) with Spring Boot. This ensures a clean separation of concerns and makes the codebase highly maintainable.

### Database / Persistence: 
Used an in-memory database (H2) via Spring Data JPA. It satisfies the persistence requirement while keeping the setup lightweight.

### PATCH Update Logic: 
Used the wrapper class Boolean instead of the primitive boolean for the enabled field. This is critical for PATCH requests, as it ensures that if the field is omitted in the JSON payload, it maps to null rather than defaulting to false, thus preventing unintended state changes.

### Error Handling: 
Implemented a @RestControllerAdvice (GlobalExceptionHandler) combined with custom runtime exceptions (FlagNotFoundException, DuplicateFlagException). This approach keeps the controllers clean and ensures the API returns proper semantic HTTP status codes (e.g., 404 Not Found for missing resources, 409 Conflict for duplicate names).

### Evaluate Endpoint: 
Uses a lightweight structure (Map.of()) to return only the specific evaluation data (name and enabled status) rather than exposing the entire database entity, which saves bandwidth and prevents data leakage.

### Testing Strategy
Since the goal was to focus on the core functionality, I prioritized Integration Testing of the web layer using MockMvc.

What was tested: The critical paths—fetching all flags, creating a flag, looking up non-existent flags, and evaluating a flag.

Why: These tests provide the highest confidence that the API contracts are respected and the application boots successfully, without getting bogged down in brittle unit tests for boilerplate code.

### Future Improvements (With more time)
If I had more time within the budget, I would add the following features:

Containerization: Add a Dockerfile and docker-compose.yml to run the application in an isolated container.

API Documentation: Integrate springdoc-openapi-starter-webmvc-ui to auto-generate an interactive Swagger UI documentation for all endpoints.

CI/CD: Setup a simple GitHub Actions workflow to run the test suite on every commit or pull request.

Environment-Specific Flags: Extend the data model to support different flag values depending on the context/environment (e.g., dev vs. prod).
