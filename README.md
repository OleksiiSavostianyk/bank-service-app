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



To run the application:

1.	Start the database (PostgreSQL)
Now the PostgreSQL container is included in docker-compose.yml, so you don’t need to run it manually. Just use Docker Compose — it will start automatically.

2.	Make sure the backend (JAR) is built
Before running the whole app, the backend (JAR) must be compiled. You can do this using the build section already configured in the Compose file.

3.	Run the entire application
Use the command below — it will build and start everything (backend, frontend, and PostgreSQL):

     docker-compose up --build





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
