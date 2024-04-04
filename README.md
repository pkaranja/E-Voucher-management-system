# Zawadi
### E-Voucher Management System Backend API

Welcome to Zawadi E-Voucher Management System Backend API repository! This API is built using the Spring Boot framework to provide robust and scalable solutions for managing electronic vouchers.

## Overview
The E-Voucher Management System Backend API serves as the backbone for handling various operations related to electronic vouchers. It offers endpoints for creating, updating, retrieving, and deleting vouchers, as well as functionalities for managing users and transactions associated with vouchers.

## Features
- **User Management**: Register, authenticate, and manage user accounts.
- **Voucher Management**: Create, update, retrieve, and delete vouchers.
- **Transaction Management**: Track and manage transactions associated with vouchers.
- **Security**: Implement secure authentication and authorization mechanisms to protect sensitive data.
- **Scalability**: Designed to scale efficiently to handle a large volume of transactions and users.

## Technologies Used
- Spring Boot: A powerful framework for building Java-based applications.
- Spring Security: Provides authentication and authorization functionalities.
- Spring Data JPA: Simplifies database operations using the Java Persistence API.
- MySQL: A relational database management system for storing application data.
- RESTful API: Exposes endpoints following RESTful principles for seamless integration with frontend applications.

## Installation
1. Clone the repository: `git clone <repository-url>`
2. Navigate to the project directory: `cd evoucher-management-backend`
3. Build the project: `mvn clean install`
4. Run the application: `java -jar target/evoucher-management-backend.jar`

## Configuration
- **Database Configuration**: Update `application.properties` file with your MySQL database connection details.
- **Security Configuration**: Configure authentication and authorization settings in `SecurityConfig.java`.

## API Endpoints
- `/api/auth/signup`: Register a new user.
- `/api/auth/signin`: Authenticate user and generate access token.
- `/api/vouchers`: Manage vouchers (GET, POST, PUT, DELETE).
- `/api/users`: Manage users (GET, POST, PUT, DELETE).
- `/api/transactions`: Manage transactions (GET, POST, PUT, DELETE).

## Documentation
For detailed API documentation and usage examples, refer to the Swagger documentation provided within the project.

## Contributing
Contributions to enhance the E-Voucher Management System Backend API are welcome! Please feel free to fork the repository, make improvements, and submit pull requests.

## License
This project is licensed under the [MIT License](LICENSE).

Thank you for using the E-Voucher Management System Backend API!

