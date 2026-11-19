# Leave Management API

Spring Boot REST API for submitting and managing employee leave requests. Data is stored in an in-memory `ConcurrentHashMap`; no database is required.

## Technology
- Java 17
- Spring Boot 3.5.7
- Maven
- Spring Web and Bean Validation
- JUnit 5, Mockito and MockMvc

## Architecture
`HTTP Request -> Controller -> Service -> Repository -> In-memory storage`

- **Controller:** REST endpoints and HTTP responses.
- **Service:** business logic.
- **Repository:** in-memory data operations.
- **Model:** LeaveRequest, LeaveType and LeaveStatus.
- **DTO:** request and response payloads.
- **Exception:** custom not-found exception and global error handling.
- **Configuration:** application.yml and dev profile.

## Run
Requirements: JDK 17+ and Maven 3.6+.

```bash
mvn clean install
mvn spring-boot:run
```

Base URL: `http://localhost:8080`. The `dev` profile is activated in `application.yml`.

## Endpoints

| Method | Endpoint | Description | Status |
|---|---|---|---|
| GET | `/leaves` | Retrieve all requests | 200 |
| GET | `/leaves/{id}` | Retrieve request by ID | 200 |
| POST | `/leaves` | Create request | 201 |
| PUT | `/leaves/{id}` | Update request | 200 |
| DELETE | `/leaves/{id}` | Delete request | 204 |

Leave types: `ANNUAL`, `SICK`, `CASUAL`, `MATERNITY`, `PATERNITY`, `UNPAID`.

Leave statuses: `PENDING`, `APPROVED`, `REJECTED`, `CANCELLED`. New requests start as `PENDING`. On update, status is optional; if omitted, the existing status is retained.

## Example API calls

Create:
```bash
curl -i -X POST http://localhost:8080/leaves \
  -H "Content-Type: application/json" \
  -d '{"employeeId":"EMP-101","leaveType":"ANNUAL","startDate":"2026-12-01","endDate":"2026-12-03","reason":"Family vacation"}'
```

Get all:
```bash
curl http://localhost:8080/leaves
```

Get by ID:
```bash
curl http://localhost:8080/leaves/1
```

Update:
```bash
curl -i -X PUT http://localhost:8080/leaves/1 \
  -H "Content-Type: application/json" \
  -d '{"employeeId":"EMP-101","leaveType":"ANNUAL","startDate":"2026-12-02","endDate":"2026-12-04","reason":"Updated dates","status":"PENDING"}'
```

Delete:
```bash
curl -i -X DELETE http://localhost:8080/leaves/1
```

## Validation and errors
- Required: employeeId, leaveType, startDate, endDate and reason.
- Reason is limited to 500 characters.
- endDate must be on or after startDate.
- Unknown IDs return 404; invalid input returns 400.
- Invalid JSON, dates or enum values return 400.

Example error:
```json
{
  "timestamp": "2026-11-19T10:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "validationErrors": {"employeeId": "employeeId is required"}
}
```

## Tests
Run the service unit tests and MockMvc controller tests:
```bash
mvn test
```

The tests cover default PENDING status, not-found behavior, deletion, successful API creation and validation errors.

## Notes
- In-memory data is lost when the application restarts.
- A database and authentication are not required by this assignment.
- A production version should add persistence, authentication/authorization, audit history, pagination and role-based approval rules.
