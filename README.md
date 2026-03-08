# 🎓 Academy REST API

Backend system for academic institution management built with **Java 17** and **Spring Boot 3**.

The project implements a **hybrid persistence architecture** combining relational and NoSQL databases to leverage the strengths of both approaches.

The system manages **students, teachers, courses, enrollments, courses schedule and reviews**, while ensuring secure authentication using **JWT** and role-based authorization.

---

# 🚀 Project Overview

**Academy API** is designed to simulate a real academic management backend used by educational institutions.

It supports:

* Student and teacher management
* Course creation and enrollment
* Grade assignment
* Student feedback and reviews
* Secure authentication and authorization

The application uses **MySQL for transactional academic data** and **MongoDB for flexible feedback data**, orchestrated with **Docker**.

---

# 🏗️ System Architecture

The backend follows a modular architecture with hybrid persistence.

```
Client (Frontend / Postman)
            │
            ▼
      Spring Boot API
            │
     ┌──────┴────────┐
     ▼               ▼
  MySQL           MongoDB
Academic Data      Reviews
```

### MySQL

Stores transactional academic information:

* Users
* Courses
* Courses Schedule
* Enrollments
* Students
* Teachers

### MongoDB

Stores flexible data:

* Teacher reviews

---

# 🛠️ Technology Stack

## Backend

* Java 17
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Spring Data MongoDB
* Maven

## Infrastructure

* Docker
* Docker Compose

## Databases

* MySQL
* MongoDB

## Development Tools

* IntelliJ IDEA
* Git
* Postman

---

# 🔐 Security

Authentication and authorization are implemented using **Spring Security** and **JWT tokens**.

### Features

* Stateless authentication
* Role-based authorization
* Password hashing with BCrypt
* Secure logout using **token blacklist**
* Method-level security using `@PreAuthorize`

### Roles

```
ADMINISTRATOR
TEACHER
STUDENT
```

---

# 📚 API Documentation

The API is documented using **Swagger / OpenAPI**.

After starting the application, the interactive documentation is available at:

```
http://localhost:9191/api/v1/swagger-ui/index.html
```

Swagger allows you to:

* Explore all endpoints
* Inspect request and response schemas
* Test endpoints directly from the browser

---

# 📊 Monitoring (Spring Boot Actuator)

The application includes **Spring Boot Actuator** for basic monitoring and health checks.

Available endpoints:

```
GET /actuator/health
GET /actuator/info
GET /actuator/metrics
```

Example:

```
http://localhost:9191/api/v1/actuator/health
```

These endpoints help monitor the application status and are commonly used in production environments.

---

# ✨ Core Functionalities

## Authentication

```
POST /auth/register-student
POST /auth/register-teacher
POST /auth/login
POST /auth/logout
GET  /auth/validate-token
```

Provides secure JWT authentication and token invalidation on logout.

---

## Course

```
GET    /courses
POST   /courses
PUT    /courses/{id}
DELETE /courses/{id}
```

---

## Course Schedule

```
GET    /schedules
POST   /schedules
PUT    /schedules/{courseId}
DELETE /schedules/{id}
```

---

## Enrollments

```
POST  /enrollments
GET   /enrollments/me
PATCH /enrollments/{id}/grade
```

---

## Students

```
GET    /students/me
PUT    /students/me
GET    /students
GET    /students/{id}
POST   /students
PUT    /students/{id}
DELETE /students/{id}
```

---

## Teachers

```
GET    /teachers/me
PUT    /teachers/me
GET    /teachers
POST   /teachers
PUT    /teachers/{id}
DELETE /teachers/{id}
```

### Business Rule

Only the **assigned teacher** or an **administrator** can assign grades to a student enrollment.

---

## Teacher Reviews (MongoDB)

```
POST /reviews
GET  /reviews
GET  /reviews/{id}
GET  /reviews/my-reviews
```

The review system uses **MongoDB compound indexes** to enforce the rule:

> A student can review a teacher only once per course and academic semester.

---

# 📁 Project Structure

The project follows a **modular domain-based architecture**.

```
src/main/java/com/cehn17/academy

admin
common
config
course
courseschedule
enrollment
exception
student
teacher
teacherreview
user
```

Each module typically contains:

```
controller
service
repository
entity
dto
mapper
```

This approach improves **maintainability, scalability and separation of concerns**.

---

# ⚙️ Installation & Running

You can run the entire environment locally using **Docker**.

## Prerequisites

* Java 17+
* Maven
* Docker
* Docker Compose

---

## 1️⃣ Clone the repository

```
git clone https://github.com/cehn17/academy-api.git
cd academy-api
```

---

## 2️⃣ Start the databases

```
docker-compose up -d
```

This starts:

* MySQL
* MongoDB

---

## 3️⃣ Run the application

```
mvn spring-boot:run
```

---

## 4️⃣ Access the API

API Base URL:

```
http://localhost:9191/api/v1
```

Swagger Documentation:

```
http://localhost:9191/api/v1/swagger-ui/index.html
```

---

# 🧪 Testing the API

The API can be tested using:

* Swagger UI
* Postman
* cURL

Swagger is recommended for quickly exploring the endpoints.

---

# 🗺️ Roadmap / Future Improvements

The project is functional and follows a professional architecture, with plans for continuous evolution:

* **Database Migration (Flyway):** SQL schema versioning to replace `ddl-auto`.
* **Email Service:** Asynchronous notifications for password recovery.
* **Automated Testing:** Integration of **TestContainers** for CI/CD.
* **Frontend:** Web client development (React/Angular).

---

# 👨‍💻 Author

**Cesar Niveyro**

Backend Developer — Java / Spring Boot

GitHub
https://github.com/cehn17

---

⭐ If you find this project interesting, feel free to explore the code or suggest improvements.
