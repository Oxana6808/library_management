📚 Library Management System
🌟 Overview
This is a simple Library Management System built with Spring Boot, Thymeleaf, and H2 Database. It allows users to register, log in, and manage a collection of books (add, edit, delete). The application is designed for small libraries or personal use, providing a user-friendly interface to track book details such as title, author, and availability.
✨ Features

User Registration & Login: Securely register and log in using email and password.
Book Management:
Add new books with title, author, and availability status.
Edit or delete existing books.
View all books in a clean table format.


Security: Secure authentication with Spring Security.
Database: In-memory H2 database for storing user and book data.

🛠️ Requirements

Java 17 or higher
Maven (for dependency management and building the project)
Git (to clone the repository)

🚀 Installation and Setup

Clone the Repository:
git clone https://github.com/Oxana6808/library_management.git
cd library-management


Build the Project:
mvn clean install


Run the Application:
mvn spring-boot:run


Access the Application:

Open your browser and go to http://localhost:8080/.
The default landing page (/) will show options to log in or register.


Access the H2 Database Console (Optional):

Go to http://localhost:8080/h2-console.
Use the following settings:
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (leave blank)


View the USERS and BOOKS tables to inspect the data.



📖 Usage

Register a New User:

Navigate to /register.
Fill in your email and password.
Example: Email: user@example.com, Password: password123.




Log In:

Go to /login and enter your credentials.
Upon successful login, you’ll be redirected to the dashboard (/dashboard).


Manage Books:

On the dashboard, you can:
Add a New Book: Fill in the title, author, and availability.
Edit or Delete Books: Use the actions in the table.


Use the Logout button to return to the homepage.



📂 Project Structure

src/main/java/bookproject/library_management/
config/: Security configuration for Spring Security.
controller/: Controllers for handling user and book requests.
model/: Entity classes (User, Book).
repository/: JPA repositories for database operations.
service/: Service layer for business logic.


src/main/resources/templates/: Thymeleaf templates for the UI (index.html, login.html, dashboard.html, etc.).
src/main/resources/application.properties: Configuration for H2 database and Spring settings.

🔑 Default User
For testing purposes, you can use the following credentials after the first run (if you populate the database):

Email: admin@example.com
Password: admin123

🤝 Contributing
Contributions are welcome! To contribute:

Fork the repository.
Create a new branch:git checkout -b feature/your-feature


Make your changes and commit:git commit -m "Add your feature"


Push to your branch:git push origin feature/your-feature


Open a Pull Request on GitHub.

📜 License
This project is licensed under the MIT License.
👩‍💻 Author
Developed by Oksana (Oxana6808).
