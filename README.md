# Job Application Tracker

A comprehensive Spring Boot application designed to help users track and manage their job applications efficiently. This project provides a centralized platform to monitor application status, interviews, and job opportunities.

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Authentication](#authentication)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

- **User Authentication & Authorization**: Secure JWT-based authentication system
- **Job Application Tracking**: Create, update, and track job applications
- **Application Status Management**: Monitor application stages (Applied, Interview, Offer, Rejected, etc.)
- **Interview Scheduling**: Keep track of interview dates and details
- **Company Information**: Store and manage company details
- **Search & Filter**: Easily search and filter applications by various criteria
- **Data Validation**: Built-in validation for all input data
- **Secure API**: Spring Security integration with JWT tokens

## 🛠 Tech Stack

- **Backend**: Spring Boot 4.1.1
- **Java Version**: Java 21
- **Database**: MySQL
- **ORM**: Spring Data JPA
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: Spring Security
- **Build Tool**: Maven
- **Additional Libraries**:
  - Lombok (for reducing boilerplate code)
  - Spring Validation

### Dependencies Overview

```xml
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-validation
- spring-boot-starter-webmvc
- mysql-connector-j
- jjwt (JWT for authentication)
- lombok
```

## 📋 Prerequisites

Before running this project, ensure you have the following installed:

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+
- Git

## 🚀 Installation

1. **Clone the Repository**

```bash
git clone https://github.com/Aaditya-17/Job-Application-tracker.git
cd Job-Application-tracker
```

2. **Create Database**

```sql
CREATE DATABASE job_tracker_db;
```

3. **Update Application Configuration**

Edit `src/main/resources/application.properties` or `application.yml`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/job_tracker_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# JWT Configuration
jwt.secret=your_secret_key_here
jwt.expiration=86400000
```

4. **Build the Project**

```bash
mvn clean install
```

## ⚙️ Configuration

### Database Configuration

The application uses MySQL as the default database. Update your connection details in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/job_tracker_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### JWT Configuration

Configure JWT settings for authentication:

```properties
jwt.secret=your_very_secure_secret_key_here
jwt.expiration=86400000  # Token expiration time in milliseconds
```

## 🏃 Running the Application

### Using Maven

```bash
mvn spring-boot:run
```

### Using Maven Wrapper (Cross-platform)

**On Linux/macOS:**
```bash
./mvnw spring-boot:run
```

**On Windows:**
```bash
mvnw.cmd spring-boot:run
```

### After Starting

The application will be available at: `http://localhost:8080/api`

## 📁 Project Structure

```
Job-Application-tracker/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/JobApplicationTracker/
│   │   │       ├── controller/          # REST Controllers
│   │   │       ├── service/             # Business Logic
│   │   │       ├── repository/          # Data Access Layer
│   │   │       ├── entity/              # JPA Entities
│   │   │       ├── dto/                 # Data Transfer Objects
│   │   │       ├── security/            # JWT & Security Config
│   │   │       ├── exception/           # Custom Exceptions
│   │   │       └── util/                # Utility Classes
│   │   └── resources/
│   │       ├── application.properties   # Configuration
│   │       └── db/
│   └── test/                            # Test Classes
├── pom.xml                              # Maven Configuration
├── mvnw                                 # Maven Wrapper (Linux/macOS)
├── mvnw.cmd                             # Maven Wrapper (Windows)
└── README.md                            # This file
```

## 🔌 API Documentation

### Authentication Endpoints

#### Register User
```
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}

Response: 201 Created
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

#### Login
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}

Response: 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 86400
}
```

### Application Endpoints

#### Get All Applications
```
GET /api/applications
Authorization: Bearer <token>

Response: 200 OK
[
  {
    "id": 1,
    "company": "TechCorp",
    "position": "Senior Java Developer",
    "status": "INTERVIEW",
    "appliedDate": "2024-01-15",
    "salary": "150000"
  }
]
```

#### Create Application
```
POST /api/applications
Authorization: Bearer <token>
Content-Type: application/json

{
  "company": "TechCorp",
  "position": "Senior Java Developer",
  "status": "APPLIED",
  "appliedDate": "2024-01-15",
  "salary": "150000",
  "jobDescription": "Looking for experienced Java developer..."
}

Response: 201 Created
```

#### Update Application
```
PUT /api/applications/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "status": "INTERVIEW",
  "interviewDate": "2024-02-20"
}

Response: 200 OK
```

#### Delete Application
```
DELETE /api/applications/{id}
Authorization: Bearer <token>

Response: 204 No Content
```

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Applications Table
```sql
CREATE TABLE applications (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  company VARCHAR(255) NOT NULL,
  position VARCHAR(255) NOT NULL,
  status VARCHAR(50),
  applied_date DATE,
  salary DECIMAL(10, 2),
  job_description LONGTEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

## 🔐 Authentication

This project uses JWT (JSON Web Tokens) for authentication:

1. **Register**: Create a new user account
2. **Login**: Receive a JWT token
3. **Authorization**: Include the token in the `Authorization` header:
   ```
   Authorization: Bearer <your_jwt_token>
   ```

All protected endpoints require a valid JWT token in the request header.

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the MIT License. See the LICENSE file for more details.

## 📧 Contact

For questions or support, please reach out to:
- **Developer**: Aaditya-17
- **Repository**: [Job-Application-tracker](https://github.com/Aaditya-17/Job-Application-tracker)

## 🗺️ Roadmap

- [ ] Add email notifications for application updates
- [ ] Implement resume management system
- [ ] Add interview preparation resources
- [ ] Create salary comparison feature
- [ ] Add analytics and statistics dashboard
- [ ] Implement bulk import from job sites
- [ ] Add mobile application

---

**Happy Job Hunting! 🎯**
