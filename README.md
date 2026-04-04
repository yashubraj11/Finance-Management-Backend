# Finance-Management-Backend

A secure and scalable Spring Boot REST API for managing financial records with Role-Based Access Control (RBAC), JWT authentication, and API documentation using Swagger.

--------------------------------------------------

Features

User and Role Management
- Roles: ADMIN, ANALYST, VIEWER

Financial Records CRUD
- Create, Read, Update, Delete financial records

Advanced Filtering
- Filter records by:
  - Type (INCOME / EXPENSE)
  - Category
  - Date range

Dashboard APIs
- Total income, expenses, and balance
- Category-wise expense summary
- Monthly trends
- Recent transactions

Role-Based Access Control
- ADMIN: Full access
- ANALYST: Read and filter access
- VIEWER: Dashboard access only

Input Validation
- Field-level validation using Jakarta Validation

Global Exception Handling
- Handles 400, 403, 404, and 500 errors

Database Integration
- MySQL with Spring Data JPA

API Documentation
- Swagger (OpenAPI) for testing endpoints

--------------------------------------------------

Tech Stack

Backend: Spring Boot (Java 17)  
Security: Spring Security with JWT Authentication  
Database: MySQL  
ORM: Spring Data JPA (Hibernate)  
API Documentation: Swagger (OpenAPI)

--------------------------------------------------

Project Structure

com.example.financeapp

controller     - REST Controllers  
service        - Business Logic  
repository     - Database Access Layer  
model          - Entities and Enums  
security       - JWT and Security Configuration  
config         - Application and Swagger Configuration  
exception      - Global Exception Handling  

--------------------------------------------------

API Endpoints Overview (with JSON Examples)

Authentication

POST /auth/login

Request Body:
{
  "email": "admin@test.com",
  "password": "Admin@123"
}

Response:
{
  "token": "JWT_TOKEN"
}

--------------------------------------------------

Users

POST /users (ADMIN only)

Request Body:
{
  "name": "John Doe",
  "email": "john@test.com",
  "password": "John@123",
  "role": "ANALYST"
}

GET /users
(No request body)

--------------------------------------------------

Records

POST /records (ADMIN)

Request Body:
{
  "amount": 1000,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-04",
  "description": "Monthly salary",
  "userId": 1
}

GET /records
(No request body)

GET /records/page?page=0&size=5
(No request body)

GET /records/filter?type=INCOME&category=Salary&startDate=2026-04-01&endDate=2026-04-30
(No request body)

PUT /records/{id} (ADMIN)

Request Body:
{
  "amount": 1200,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-04",
  "description": "Updated salary",
  "userId": 1
}

DELETE /records/{id}
(No request body)

--------------------------------------------------

Dashboard

GET /dashboard/summary
(No request body)

GET /dashboard/category-summary
(No request body)

GET /dashboard/recent?limit=5
(No request body)

GET /dashboard/monthly-trend
(No request body)

GET /dashboard/full
(No request body)

--------------------------------------------------

Authentication

- JWT-based authentication is used.
- Login using /auth/login to receive a token.
- Pass the token in headers:

Authorization: Bearer <your_token>

--------------------------------------------------

Swagger Usage

- Access Swagger UI:
  http://localhost:8081/swagger-ui/index.html

- Steps:
  1. Login via /auth/login
  2. Copy the JWT token
  3. Click "Authorize" in Swagger
  4. Enter: Bearer <token>
  5. Access secured endpoints

--------------------------------------------------

Configuration

The application supports environment-based configuration.

Optional environment variables:

PORT=8081  
DB_URL=jdbc:mysql://localhost:3306/finance_db12  
DB_USERNAME=root  
DB_PASSWORD=root  
JWT_SECRET=your_secret_key  

If not provided, default values in application.properties will be used.

--------------------------------------------------

Setup Instructions

1. Clone the repository  
2. Configure MySQL database  
3. Update application.properties if needed  
4. Run the application using Spring Boot  
5. Access APIs via Postman or Swagger  

--------------------------------------------------

Technical Decisions and Trade-offs

- Spring Boot was chosen for rapid backend development and strong ecosystem support.
- Spring Security with JWT was implemented for stateless authentication.
- MySQL was used for reliable relational data storage.
- Layered architecture (Controller-Service-Repository) improves maintainability.
- In-memory filtering is used for flexibility, though database-level filtering could improve performance for large datasets.
- Environment variables are used for configurable and secure deployment.

--------------------------------------------------

Additional Notes

- JWT tokens expire after a fixed duration (default: 1 hour).
- Proper role-based restrictions are enforced at endpoint level.
- Custom Access Denied handling is implemented for better error responses.
- This project is designed for learning and assessment purposes and can be extended further for production use.

--------------------------------------------------
