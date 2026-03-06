# 🛍️ SalesSavvy Backend

A full-featured and secure **RESTful backend service for an e-commerce platform** built using **Java 17 and Spring Boot**.

SalesSavvy enables businesses to efficiently manage **inventory, customers, orders, and payments** while ensuring secure access through **JWT authentication and role-based access control (RBAC)**.

---

## ✨ Features

## 🔧 Admin Features

- Manage **products, categories, users, and orders**
- Upload and manage **product images**
- Generate **business reports** (daily, monthly, yearly, overall)
- Advanced **search and filtering**
- **Role-based access control** for admin operations

## 🛒 Customer Features

- Browse products and **filter by category**
- Add/remove items in **shopping cart**
- Place orders and view **order history**
- Secure **payment integration using Razorpay (Test Mode)**
- Manage **user profile**
- Secure endpoints with **JWT authentication**

---

## 🔐 Security & Authentication

- **JWT tokens** generated during login
- Tokens stored securely in **HttpOnly cookies**
- **Role-based authorization (Admin / Customer)**
- Request filtering through **JWT authentication filters**

---

## 💻 Tech Stack

| Technology | Purpose |
|------------|--------|
| Java 17 | Core programming language |
| Spring Boot | Backend REST API framework |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database ORM |
| MySQL | Primary database |
| JWT (JJWT) | Token-based authentication |
| Razorpay | Payment gateway integration |
| Docker | Containerization |
| Maven | Dependency management |

---

## 🚀 Setup & Installation (Docker Recommended)

The entire application and database are containerized using **Docker Compose**.

## Prerequisites

- Docker
- Docker Compose

## Steps



### Clone the repository

```bash
git clone https://github.com/your-username/sales-savvy-backend.git
cd sales-savvy-backend
```

### Build and run containers

```bash
docker-compose up --build
```

## 📍 Access Points
| Service | URL |
|--------|-----|
Backend API | http://localhost:8080 |
MySQL Database | localhost:3306 |

## 🗄 Database Details

Database Schema: **Ecommerce**
Tables:
- products
- categories
- users
- orders
- order_items
- product_images
- jwt_tokens

##🌐 API Endpoints
###Authentication
### Authentication

| Method | Endpoint | Description |
|------|-----------|-------------|
POST | /api/users/register | Register a new user |
POST | /api/auth/login | Login and receive JWT token |
POST | /api/auth/logout | Logout and clear JWT |

Customer APIs
Method	Endpoint	Description
GET	/api/products	Browse products
POST	/api/cart/add	Add item to cart
PUT	/api/cart/update	Update cart quantity
DELETE	/api/cart/delete	Remove item from cart
GET	/api/cart/items	Get cart items
POST	/api/orders	Place order
GET	/api/orders	View user orders

Admin APIs
Method	Endpoint	Description
GET	/admin/business/daily	Daily report
GET	/admin/business/monthly	Monthly report
GET	/admin/business/yearly	Yearly report
GET	/admin/business/overall	Overall report
POST	/admin/products/add	Add product
DELETE	/admin/products/delete	Delete product
GET	/admin/users	Get all users
PUT	/admin/user/modify	Modify user

##⚙ Running Without Docker
Prerequisites
Java 17+
Maven
MySQL 8.0

### Create database

```sql
CREATE DATABASE Ecommerce;
```
Run application
mvn spring-boot:run
Application will start on
http://localhost:8080

---

##📁 Project Structure
sales-savvy-backend
│
├── src
│   ├── controller
│   ├── service
│   ├── repo
│   ├── entity
│   ├── config
│   ├── jwt
│   └── exception
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
---
👨‍💻 Author

Sohan R Baadkar

GitHub
https://github.com/Sohanbaadkar

LinkedIn
https://www.linkedin.com/in/sohan-baadkar-aa4579229/
