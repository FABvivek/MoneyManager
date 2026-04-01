# Money Manager Backend API

A comprehensive Spring Boot RESTful API for managing personal finances, including income tracking, expense management, and financial dashboard analytics.

## Project Overview

**Money Manager** is a personal finance management application backend built with Spring Boot 4.0.1 and Java 21. It provides robust APIs for users to track their income, manage expenses, categorize transactions, and view financial dashboards.

### Key Features
- 👤 **User Authentication & Profile Management** - Registration, login with JWT authentication, email activation
- 💰 **Income & Expense Tracking** - Create, retrieve, and delete income and expense records
- 🏷️ **Category Management** - Create custom categories for income and expenses
- 📊 **Financial Dashboard** - View total balance, income, expenses, and recent transactions
- 🔍 **Advanced Filtering** - Filter transactions by date range, keyword, and custom sorting
- 📧 **Email Notifications** - Integration with Brevo email service for account activation
- 🔐 **JWT Security** - Token-based authentication with secure password hashing (BCrypt)
- 🌐 **CORS Support** - Cross-origin resource sharing enabled for frontend integration

## Technology Stack

### Backend Framework & Language
- **Java 21** - Latest Java LTS version
- **Spring Boot 4.0.1** - Latest Spring Boot framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - ORM for database operations
- **Spring WebFlux** - Reactive web framework

### Database
- **MySQL** - Primary database
- **PostgreSQL** - Alternative database support (in dependencies)
- **Hibernate** - JPA implementation with auto DDL updates

### Security & Authentication
- **JWT (JSON Web Tokens)** - JJWT 0.11.5 for token generation and validation
- **BCrypt** - Password encryption

### Additional Libraries
- **Lombok** - Reduce boilerplate code with annotations
- **Apache POI 5.4.0** - Excel file support

### Email Service
- **Brevo SMTP API** - Third-party email service integration

## Project Structure

```
moneymanager-backend/
├── src/main/java/com/money/moneymanager/
│   ├── MoneymanagerApplication.java       # Application entry point
│   ├── config/
│   │   ├── SecurityConfig.java            # Spring Security configuration
│   │   └── WebClientConfig.java           # WebClient beans configuration
│   ├── controller/
│   │   ├── ProfileController.java         # User registration, login, activation
│   │   ├── CategoryController.java        # Category management
│   │   ├── ExpenseController.java         # Expense operations
│   │   ├── IncomeController.java          # Income operations
│   │   ├── DashboardController.java       # Dashboard data
│   │   ├── FilterController.java          # Transaction filtering
│   │   └── HomeController.java            # Health check endpoint
│   ├── service/
│   │   ├── ProfileService.java            # User management logic
│   │   ├── CategoryService.java           # Category business logic
│   │   ├── ExpenseService.java            # Expense business logic
│   │   ├── IncomeService.java             # Income business logic
│   │   ├── DashboardService.java          # Dashboard logic
│   │   ├── EmailService.java              # Email sending via Brevo
│   │   ├── NotificationService.java       # Notification handling
│   │   └── AppUserDetailsService.java     # User authentication service
│   ├── entity/
│   │   ├── ProfileEntity.java             # User profile entity
│   │   ├── CategoryEntity.java            # Category entity
│   │   ├── ExpenseEntity.java             # Expense entity
│   │   └── IncomeEntity.java              # Income entity
│   ├── dto/
│   │   ├── ProfileDTO.java                # Profile data transfer object
│   │   ├── AuthDTO.java                   # Authentication DTO
│   │   ├── CategoryDTO.java               # Category DTO
│   │   ├── ExpenseDTO.java                # Expense DTO
│   │   ├── IncomeDTO.java                 # Income DTO
│   │   ├── FilterDTO.java                 # Filter criteria DTO
│   │   └── RecentTransectionDTO.java      # Recent transaction DTO
│   ├── repositorty/
│   │   ├── ProfileRepository.java         # User profile repository
│   │   ├── CategoryRepository.java        # Category repository
│   │   ├── ExpenseRepository.java         # Expense repository
│   │   └── IncomeRepository.java          # Income repository
│   ├── security/
│   │   └── JwtRequestFilter.java          # JWT token validation filter
│   └── util/
│       └── JwtUtil.java                   # JWT token utilities
├── src/main/resources/
│   ├── application.properties             # Default configuration
│   └── application-prod.properties        # Production configuration
├── Dockerfile                             # Docker container configuration
├── pom.xml                                # Maven dependencies
└── README.md                              # This file
```

## Installation & Setup

### Prerequisites
- Java 21 (JDK)
- Maven 3.6+
- MySQL 8.0+ or PostgreSQL
- Brevo API Key (for email functionality)

### 1. Clone the Repository
```bash
git clone <repository-url>
cd moneymanager-backend
```

### 2. Configure Application Properties
Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/moneymanager
spring.datasource.username=root
spring.datasource.password=your_password

# JWT Configuration
jwt.secret=your_jwt_secret_key_here
jwt.expiration=86400000  # 24 hours in milliseconds

# Email Service (Brevo)
brevo.api.key=${BREVO_API_KEY}
brevo.api.url=https://api.brevo.com/v3/smtp/email

# Frontend URL
money.manager.frontend.url=http://localhost:3000
app.activation.url=http://localhost:8080

# Server Configuration
server.servlet.context-path=/api/v1.0
server.port=9090
```

### 3. Set Environment Variables
```bash
# Windows PowerShell
$env:BREVO_API_KEY = "your_brevo_api_key"
$env:JwtSecret = "your_jwt_secret_key"
$env:MONEY_MANAGER_BACKENDURL = "http://localhost:8080"

# Linux/Mac
export BREVO_API_KEY="your_brevo_api_key"
export JwtSecret="your_jwt_secret_key"
export MONEY_MANAGER_BACKENDURL="http://localhost:8080"
```

### 4. Build the Project
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
# or
java -jar target/moneymanager-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:9090/api/v1.0`

## Docker Deployment

### Build Docker Image
```bash
# Build the project first
mvn clean package

# Build Docker image
docker build -t moneymanager:latest .
```

### Run Docker Container
```bash
docker run -d \
  --name moneymanager \
  -p 9090:9090 \
  -e BREVO_API_KEY="your_api_key" \
  -e JwtSecret="your_jwt_secret" \
  -e MONEY_MANAGER_BACKENDURL="http://your-backend-url" \
  moneymanager:latest
```

## API Documentation

### Base URL
```
http://localhost:9090/api/v1.0
```

### Authentication
All endpoints (except `/health`, `/status`, `/register`, `/activate`, `/login`) require JWT Bearer token:
```
Authorization: Bearer <jwt_token>
```

---

## API Endpoints

### 1. Health & Status
Check application health

#### Health Check
```
GET /health
GET /status
```
**Response (200 OK):**
```
Application is running.
```

---

### 2. Authentication & Profile Management

#### Register User
```
POST /register
```
**Request Body:**
```json
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "profileImageUrl": "https://example.com/image.jpg"
}
```
**Response (201 Created):**
```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john@example.com",
  "profileImageUrl": "https://example.com/image.jpg",
  "createdAt": "2026-04-01T10:30:00",
  "updatedAt": "2026-04-01T10:30:00"
}
```
**Note:** A confirmation email will be sent to activate the account.

---

#### Activate Profile
```
GET /activate?token={activation_token}
```
**Response (200 OK):**
```
Profile activated successfully.
```
**Response (404 Not Found):**
```
Activation token not found or already used.
```

---

#### Login
```
POST /login
```
**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "securePassword123"
}
```
**Response (200 OK):**
```json
{
  "id": 1,
  "email": "john@example.com",
  "fullName": "John Doe",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "profileImageUrl": "https://example.com/image.jpg"
}
```
**Response (403 Forbidden):**
```json
{
  "message": "Account is not activated. Please check your email for the activation link."
}
```
**Response (400 Bad Request):**
```json
{
  "message": "Invalid email or password"
}
```

---

### 3. Categories

#### Create Category
```
POST /categories
Authorization: Bearer <jwt_token>
```
**Request Body:**
```json
{
  "name": "Groceries",
  "type": "expense",
  "icon": "🛒"
}
```
**Response (201 Created):**
```json
{
  "id": 1,
  "profileId": 1,
  "name": "Groceries",
  "type": "expense",
  "icon": "🛒",
  "createdAt": "2026-04-01T10:30:00",
  "updatedAt": "2026-04-01T10:30:00"
}
```

---

#### Get All Categories for Current User
```
GET /categories
Authorization: Bearer <jwt_token>
```
**Response (200 OK):**
```json
[
  {
    "id": 1,
    "profileId": 1,
    "name": "Groceries",
    "type": "expense",
    "icon": "🛒",
    "createdAt": "2026-04-01T10:30:00",
    "updatedAt": "2026-04-01T10:30:00"
  },
  {
    "id": 2,
    "profileId": 1,
    "name": "Salary",
    "type": "income",
    "icon": "💰",
    "createdAt": "2026-04-01T11:00:00",
    "updatedAt": "2026-04-01T11:00:00"
  }
]
```

---

#### Get Categories by Type
```
GET /categories/{type}
Authorization: Bearer <jwt_token>
```
**Path Parameters:**
- `type`: "income" or "expense"

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "profileId": 1,
    "name": "Groceries",
    "type": "expense",
    "icon": "🛒",
    "createdAt": "2026-04-01T10:30:00",
    "updatedAt": "2026-04-01T10:30:00"
  }
]
```

---

#### Update Category
```
PUT /categories/{categoryId}
Authorization: Bearer <jwt_token>
```
**Path Parameters:**
- `categoryId`: ID of the category to update

**Request Body:**
```json
{
  "name": "Food & Groceries",
  "type": "expense",
  "icon": "🍔"
}
```
**Response (200 OK):**
```json
{
  "id": 1,
  "profileId": 1,
  "name": "Food & Groceries",
  "type": "expense",
  "icon": "🍔",
  "createdAt": "2026-04-01T10:30:00",
  "updatedAt": "2026-04-01T12:00:00"
}
```

---

### 4. Expenses

#### Add Expense
```
POST /expenses
Authorization: Bearer <jwt_token>
```
**Request Body:**
```json
{
  "name": "Weekly Groceries",
  "categoryId": 1,
  "amount": 50.75,
  "date": "2026-04-01",
  "icon": "🛒"
}
```
**Response (201 Created):**
```json
{
  "id": 1,
  "name": "Weekly Groceries",
  "categoryId": 1,
  "categoryName": "Groceries",
  "amount": 50.75,
  "date": "2026-04-01",
  "icon": "🛒",
  "createdAt": "2026-04-01T10:30:00",
  "updatedAt": "2026-04-01T10:30:00"
}
```

---

#### Get Current Month Expenses
```
GET /expenses
Authorization: Bearer <jwt_token>
```
**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Weekly Groceries",
    "categoryId": 1,
    "categoryName": "Groceries",
    "amount": 50.75,
    "date": "2026-04-01",
    "icon": "🛒",
    "createdAt": "2026-04-01T10:30:00",
    "updatedAt": "2026-04-01T10:30:00"
  },
  {
    "id": 2,
    "name": "Gas",
    "categoryId": 3,
    "categoryName": "Transportation",
    "amount": 45.00,
    "date": "2026-04-02",
    "icon": "⛽",
    "createdAt": "2026-04-02T09:15:00",
    "updatedAt": "2026-04-02T09:15:00"
  }
]
```

---

#### Delete Expense
```
DELETE /expenses/{id}
Authorization: Bearer <jwt_token>
```
**Path Parameters:**
- `id`: ID of the expense to delete

**Response (204 No Content):**
```
(Empty response body)
```

---

### 5. Income

#### Add Income
```
POST /incomes
Authorization: Bearer <jwt_token>
```
**Request Body:**
```json
{
  "name": "Monthly Salary",
  "categoryId": 2,
  "amount": 3000.00,
  "date": "2026-04-01",
  "icon": "💰"
}
```
**Response (201 Created):**
```json
{
  "id": 1,
  "name": "Monthly Salary",
  "categoryId": 2,
  "categoryName": "Salary",
  "amount": 3000.00,
  "date": "2026-04-01",
  "icon": "💰",
  "createdAt": "2026-04-01T10:30:00",
  "updatedAt": "2026-04-01T10:30:00"
}
```

---

#### Get Current Month Income
```
GET /incomes
Authorization: Bearer <jwt_token>
```
**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Monthly Salary",
    "categoryId": 2,
    "categoryName": "Salary",
    "amount": 3000.00,
    "date": "2026-04-01",
    "icon": "💰",
    "createdAt": "2026-04-01T10:30:00",
    "updatedAt": "2026-04-01T10:30:00"
  },
  {
    "id": 2,
    "name": "Freelance Project",
    "categoryId": 4,
    "categoryName": "Freelance",
    "amount": 500.00,
    "date": "2026-04-05",
    "icon": "💻",
    "createdAt": "2026-04-05T14:20:00",
    "updatedAt": "2026-04-05T14:20:00"
  }
]
```

---

#### Delete Income
```
DELETE /incomes/{id}
Authorization: Bearer <jwt_token>
```
**Path Parameters:**
- `id`: ID of the income to delete

**Response (204 No Content):**
```
(Empty response body)
```

---

### 6. Dashboard

#### Get Dashboard Data
```
GET /dashboard
Authorization: Bearer <jwt_token>
```
**Response (200 OK):**
```json
{
  "totalBalance": 7404.25,
  "totalIncome": 3500.00,
  "totalExpense": 95.75,
  "recent5Incomes": [
    {
      "id": 1,
      "name": "Monthly Salary",
      "categoryId": 2,
      "categoryName": "Salary",
      "amount": 3000.00,
      "date": "2026-04-01",
      "icon": "💰",
      "createdAt": "2026-04-01T10:30:00",
      "updatedAt": "2026-04-01T10:30:00"
    }
  ],
  "recent5Expenses": [
    {
      "id": 1,
      "name": "Weekly Groceries",
      "categoryId": 1,
      "categoryName": "Groceries",
      "amount": 50.75,
      "date": "2026-04-01",
      "icon": "🛒",
      "createdAt": "2026-04-01T10:30:00",
      "updatedAt": "2026-04-01T10:30:00"
    }
  ],
  "recentTransections": [
    {
      "id": 1,
      "profileId": 1,
      "name": "Monthly Salary",
      "amount": 3000.00,
      "date": "2026-04-01",
      "type": "income",
      "icon": "💰",
      "createdAt": "2026-04-01T10:30:00",
      "updatedAt": "2026-04-01T10:30:00"
    },
    {
      "id": 1,
      "profileId": 1,
      "name": "Weekly Groceries",
      "amount": 50.75,
      "date": "2026-04-01",
      "type": "expense",
      "icon": "🛒",
      "createdAt": "2026-04-01T10:30:00",
      "updatedAt": "2026-04-01T10:30:00"
    }
  ]
}
```

---

### 7. Filtering & Search

#### Filter Transactions
```
POST /filter
Authorization: Bearer <jwt_token>
```
**Request Body:**
```json
{
  "type": "expense",
  "startDate": "2026-04-01",
  "endDate": "2026-04-30",
  "keyword": "grocer",
  "sortField": "date",
  "sortOrder": "desc"
}
```
**Query Parameters (in body):**
- `type`: "income" or "expense" (required)
- `startDate`: Start date in YYYY-MM-DD format (optional, defaults to LocalDate.MIN)
- `endDate`: End date in YYYY-MM-DD format (optional, defaults to today)
- `keyword`: Search keyword for transaction name (optional, empty string if not provided)
- `sortField`: Field to sort by - "date", "amount", "name", etc. (optional, defaults to "date")
- `sortOrder`: "asc" or "desc" (optional, defaults to "asc")

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Weekly Groceries",
    "categoryId": 1,
    "categoryName": "Groceries",
    "amount": 50.75,
    "date": "2026-04-01",
    "icon": "🛒",
    "createdAt": "2026-04-01T10:30:00",
    "updatedAt": "2026-04-01T10:30:00"
  }
]
```

**Response (400 Bad Request):**
```json
{
  "message": "Invalid type. Must be income or expense"
}
```

---

## Security

### JWT Token Flow
1. **User Registers** → Account created (inactive)
2. **Email Verification** → Activation link sent to email
3. **User Activates** → Account becomes active
4. **User Logs In** → JWT token generated and returned
5. **Use Token** → Include in Authorization header for authenticated requests

### Token Expiration
- Default expiration: 24 hours (86400000 milliseconds)
- Configurable via `jwt.expiration` property

### Password Security
- Passwords encrypted using BCrypt
- Never stored in plain text
- Validated on each authentication attempt

### CORS Configuration
- Allows cross-origin requests from all origins (*)
- Supported methods: GET, POST, PUT, DELETE, OPTIONS
- Allowed headers: Authorization, Content-Type, Accept

---

## Database Schema

### tbl_profile
```sql
CREATE TABLE tbl_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  full_name VARCHAR(255),
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  profile_image_url VARCHAR(500),
  is_active BOOLEAN DEFAULT FALSE,
  activation_token VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### tbl_categories
```sql
CREATE TABLE tbl_categories (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  type VARCHAR(50),
  icon VARCHAR(10),
  profile_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (profile_id) REFERENCES tbl_profile(id)
);
```

### tbl_expenses
```sql
CREATE TABLE tbl_expenses (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  icon VARCHAR(10),
  amount DECIMAL(15, 2),
  date DATE,
  category_id BIGINT NOT NULL,
  profile_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (category_id) REFERENCES tbl_categories(id),
  FOREIGN KEY (profile_id) REFERENCES tbl_profile(id)
);
```

### tbl_incomes
```sql
CREATE TABLE tbl_incomes (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  icon VARCHAR(10),
  amount DECIMAL(15, 2),
  date DATE,
  category_id BIGINT NOT NULL,
  profile_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (category_id) REFERENCES tbl_categories(id),
  FOREIGN KEY (profile_id) REFERENCES tbl_profile(id)
);
```

---

## Error Handling

The API returns appropriate HTTP status codes:

| Status Code | Meaning |
|-------------|---------|
| 200 | OK - Successful GET/PUT request |
| 201 | Created - Successful POST request |
| 204 | No Content - Successful DELETE request |
| 400 | Bad Request - Invalid input or missing required fields |
| 403 | Forbidden - Account not activated or unauthorized access |
| 404 | Not Found - Resource not found |
| 500 | Internal Server Error - Server error |

---

## Testing the API

### Using cURL
```bash
# Register
curl -X POST http://localhost:9090/api/v1.0/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "email": "john@example.com",
    "password": "password123"
  }'

# Login
curl -X POST http://localhost:9090/api/v1.0/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'

# Get Dashboard (with token)
curl -X GET http://localhost:9090/api/v1.0/dashboard \
  -H "Authorization: Bearer <jwt_token>"
```

### Using Postman
1. Import the API collection
2. Set environment variables for base URL and JWT token
3. Test endpoints with pre-configured requests

### Using Swagger/OpenAPI
*(Add Springdoc OpenAPI dependency for auto-generated Swagger UI)*

---

## Troubleshooting

### Database Connection Failed
- Ensure MySQL/PostgreSQL is running
- Verify connection credentials in `application.properties`
- Check database exists: `CREATE DATABASE moneymanager;`

### Email Not Sending
- Verify Brevo API key is correct
- Check email address is valid
- Ensure `brevo.api.url` is correct

### JWT Token Invalid
- Ensure token is not expired
- Include "Bearer " prefix in Authorization header
- Verify JWT secret matches in application.properties

### CORS Issues
- Frontend URL must be properly configured
- Check browser console for CORS errors
- Verify CORS configuration in `SecurityConfig.java`

---

## Contributing

1. Create a feature branch (`git checkout -b feature/amazing-feature`)
2. Commit your changes (`git commit -m 'Add amazing feature'`)
3. Push to the branch (`git push origin feature/amazing-feature`)
4. Open a Pull Request

---

## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## Contact & Support

For issues, questions, or contributions, please contact the development team or create an issue in the repository.

**Project Repository:** [Money Manager Backend](https://github.com/your-repo)

**Author:** Vivek

**Last Updated:** April 1, 2026

---

## Changelog

### Version 0.0.1-SNAPSHOT
- Initial project setup
- User authentication and registration
- Income and expense tracking
- Category management
- Dashboard analytics
- Advanced filtering
- Email notification integration

