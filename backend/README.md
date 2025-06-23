 bank-serviсe-app

Full-stack banking service application with a custom login system, built using Spring Boot and plain JavaScript. This project simulates a simplified online banking system with account management, transactions, and user registration.

Project Structure
	•	Backend: Java 17, Spring Boot, PostgreSQL, Hibernate (JPA), JUnit, Mockito, Lombok
	•	Frontend: HTML, CSS, plain JavaScript
	•	Dockerized: Backend and frontend run in separate containers via Docker Compose

Features
	•	User registration and login (with password hashing using BCryptPasswordEncoder)
	•	Account balance and invoice generation
	•	Money transfer between users
	•	View recent transactions and users interacted with
	•	Ability to add users to favorites
	•	Change password functionality
	•	RESTful API
	•	Custom error handling (without Spring Security)
	•	Fully dockerized setup (PostgreSQL expected to be running externally)

Folder Structure

bank-serviсe-app/
│
├── backend/
│   ├── src/main/java/com/alex/banking/service/app/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── exception/
│   │   ├── models/
│   │   ├── repository/
│   │   └── service/
│   ├── Dockerfile
│   └── pom.xml
│
├── frontend/
│   ├── index.html
│   ├── css/
│   └── js/
│
├── docker-compose.yml
└── README.md

Docker Setup

To run the application:
	1.	Build images:

docker build -t my-backend ./backend
docker build -t my-frontend ./frontend

	2.	Run with Docker Compose:

docker-compose up

PostgreSQL must be running in a separate container or externally. You can modify docker-compose.yml to include a DB service if needed.

Testing
	•	Unit tests are written using JUnit and Mockito
	•	Services are tested in isolation
	•	Mocking is used for repositories

Technologies Used
	•	Java 17
	•	Spring Boot
	•	JPA / Hibernate
	•	PostgreSQL
	•	Docker, Docker Compose
	•	JUnit, Mockito
	•	Lombok
	•	HTML / CSS / JavaScript

Author

Oleksii Savostianyk

GitHub repository: https://github.com/OleksiiSavostianyk/bank-servise-app.git