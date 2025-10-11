🛍️ SalesSavvy Backend
A full-featured, robust, and secure RESTful service for an e-commerce platform built with Spring Boot and Java 17.

SalesSavvy is designed to efficiently manage inventory, process orders, handle customer data, and secure transactions, featuring JWT-based authentication and role-based access control.

✨ Features
Admin Features
Management: Manage products, categories, users, orders, and product images.

Reporting: Generate detailed business reports (daily, monthly, yearly, overall).

Search & Filter: Advanced filtering and searching for orders, products, and users.

Security: Role-based access control secured by JWT authentication.

Customer Features
Browsing: Browse all products with optional filtering by category.

Cart Management: Add, update, or remove items in the shopping cart.

Order Processing: Place orders and view detailed order history.

Payments: Secure payment processing via Razorpay (Test Mode).

Authentication: JWT token authentication for all secure endpoints.

Profile: View and manage personal profile information.

Security & Authentication
JWT Tokens: Issued upon login and stored securely in HttpOnly cookies.

Access Control: Every request is authenticated and authorized based on user roles (Customer/Admin).

💻 Tech Stack
Technology	Version	Purpose
Java	17	Core programming language.
Spring Boot	Latest	Framework for building the REST API.
MySQL	8.0	Primary data store.
JWT		Token-based authentication and authorization.
Razorpay		Payment gateway integration (Test Mode).
Docker & Docker Compose		Containerization and easy deployment.
Maven		Dependency management and build tool.

Export to Sheets
🚀 Setup & Installation (Recommended)
The entire application, including the MySQL database, is fully dockerized for immediate setup.

Prerequisites
Docker and Docker Compose installed.

Steps
Clone the Repository:

Bash

git clone https://github.com/your-username/sales-savvy-backend.git
cd sales-savvy-backend
Build and Run Containers:
This command will build the Spring Boot application, set up the MySQL database, and start both services.

Bash

docker-compose up --build
Access Points
Service	Access URL	Credentials
Backend API	http://localhost:8080	Requires Registration/Login
MySQL Database	localhost:3306	User: root, Password: SQL Password

Export to Sheets
Database Details
Schema: Ecommerce

Tables: products, categories, users, orders, orderitems, productimages, jwt_tokens

Stopping the Services
To stop and remove the containers, networks, and volumes:

Bash

docker-compose down
⚙️ Running Without Docker
If you prefer to run the backend directly on your machine:

Prerequisites
Java 17+ installed.

Maven installed.

A running MySQL 8.0 instance on localhost:3306.

A database named Ecommerce created in MySQL.

Steps
Start MySQL: Ensure your MySQL service is running with the specified host, port, and credentials.

Bash

mysql -u root -p
# Enter SQL Password
CREATE DATABASE Ecommerce;
(Note: You will need to configure your application.properties or application.yml file to run on port 8080 with the correct JDBC URL and credentials if they differ from the default.)

Run the Application:

Bash

mvn spring-boot:run
The application will start on http://localhost:8080.

🌐 API Endpoints
All endpoints are secured and require a valid JWT token in an HttpOnly cookie, obtained via the /api/auth/login endpoint.

Authentication & Core Endpoints
Method	Endpoint	Description
POST	/api/users/register	Register a new user
POST	/api/auth/login	Login user, receive JWT token in cookie
POST	/api/auth/logout	Logout user, clear JWT cookie
GET	/api/profile/info	Get profile info for authenticated user

Export to Sheets
Customer Endpoints
Method	Endpoint	Description
GET	/api/products	Browse products with optional category filters
POST	/api/cart/add	Add item to cart
PUT	/api/cart/update	Update item quantity in cart
DELETE	/api/cart/delete	Remove item from cart
GET	/api/cart/items	Get current cart items
GET	/api/cart/items/count	Get total number of cart items
POST	/api/orders	Place an order
GET	/api/orders	Get orders for authenticated user
POST	/api/payment/create	Create Razorpay order ID
POST	/api/payment/verify	Verify Razorpay payment signature

Export to Sheets
Admin Endpoints
Method	Endpoint	Description
GET	/admin/business/daily	Daily business report
GET	/admin/business/monthly	Monthly business report
GET	/admin/business/yearly	Yearly business report
GET	/admin/business/overall	Overall business report
POST	/admin/products/add	Add a new product
DELETE	/admin/products/delete	Delete a product
GET	/admin/users	Get all users
PUT	/admin/user/modify	Modify user details
POST	/admin/user/getbyid	Get user by ID

📁 Project Structure
sales-savvy-backend/
│
├── src/               # Application source code (Java/Spring)
├── config/            # Configuration files (e.g., application.yml)
├── Dockerfile         # Backend Dockerfile (Builds the Spring Boot JAR)
├── docker-compose.yml # Docker configuration (Backend + MySQL)
├── pom.xml            # Maven dependency file
└── README.md          # Project documentation
