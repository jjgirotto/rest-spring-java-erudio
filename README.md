# Restful API
## About
This project consists of creating a RESTful API for managing people, books and files.
The application uses Spring Boot to build a RESTful API, with Spring Data JPA enabling seamless relational database integration through Java entities and repositories. Spring HATEOAS ensures API responses follow hypertext REST principles, supporting smooth resource navigation. Authentication and authorization are configured with `spring-boot-starter-security` to secure API endpoints, while `java-jwt` facilitates JWT-based stateless authentication. Additionally, `springdoc-openapi-starter-webmvc-ui` provides automatic, interactive API documentation via Swagger UI for easy endpoint testing. The application includes CORS configurations, allowing secure cross-origin requests, and supports JSON, XML, and YAML for flexible data interchange and configuration.

### Used Technologies
* Java: A high-level, object-oriented programming language widely used for building server-side applications, web services, and Android applications.

* Spring Boot: A framework that simplifies the development of Java applications by providing built-in features for dependency injection, configuration, and microservices support.

* Spring Doc: A library that integrates Swagger for automatic generation of API documentation in Spring Boot applications, making it easier to interact with RESTful services.

* DozerMapper: A Java library that simplifies object mapping by automating the conversion between DTOs and entity models, particularly in layered architectures.

* h2 database: A lightweight, in-memory database often used for development and testing purposes in Java applications.

* JUnit: A widely-used testing framework for Java applications, enabling developers to write unit tests to validate the functionality of their code.

* Testcontainers: A Java library that provides lightweight, throwaway instances of databases, web browsers, or any other containerized service, used for integration testing to ensure compatibility with production-like environments.

* Mockito: A testing framework for Java that allows developers to create and manage mock objects in unit tests, facilitating isolated and independent testing of specific classes or components.

* Rest Assured: A Java library for testing RESTful services, providing a domain-specific language (DSL) to make API testing simple and expressive.

* JPA: The Java Persistence API, a specification that provides object-relational mapping (ORM) to manage relational data in Java applications.

* MySQL: A popular open-source relational database management system used for storing and managing data in web applications.

* MySQL Workbench: A visual tool for database architects and developers that provides SQL development, data modeling, and comprehensive administration features for MySQL databases.

* Flyway: A version control tool for databases that enables developers to track and apply schema migrations in a consistent manner.

* Postman: A tool used for API testing and development, enabling users to send HTTP requests, inspect responses, and automate API tests.

* Docker: A platform that allows developers to build, deploy, and manage containerized applications, facilitating consistent development environments and simplified application distribution.

* AWS (Amazon Web Services): A comprehensive cloud platform used for deploying, scaling, and managing applications, with services such as Amazon EC2 for compute resources, Amazon RDS for managed relational databases, and Amazon ECR for Docker image storage and management.

**IDE: IntelliJ IDEA Ultimate Edition 2024.2**

## Requirements
To use the project on your machine, the following tools must be installed and configured beforehand:

- Java Development Kit (JDK) 21 ou superior
- Apache Maven
- MySQL 8.0.29
- MySQL Workbench 8.0
- Postman
- Docker

## Installation guide
Follow the steps below to download, configure, and run the project in your environment:
1. **Clone the repository**
```bash
git clone https://github.com/jjgirotto/rest-spring-java-erudio.git
 ```
```bash
cd rest-spring-java-erudio
 ```
Navigate to the project directory

2. **Install dependenciess**

 ```bash
mvn clean install
 ```

2. **Run the application**

Start the Spring Boot application:
 ```bash
mvn spring-boot:run
 ```

## Database
The application will create the database and Flyway is responsible for automatically create tables, and populating it with some data.

## Collections
You can import the collections to test the application on your Postman web or app.

## API Endpoints

### Resume
| CATEGORY       | HTTP METHOD | ENDPOINT                                   | ACTION                                  |
|----------------|-------------|--------------------------------------------|-----------------------------------------|
| Person         | POST        | /api/person/v1                             | Adds a new Person                       |
| Person         | GET         | /api/person/v1/{id}                        | Finds a Person                          |
| Person         | GET         | /api/person/v1                             | Finds all People                        |
| Person         | GET         | /api/person/v1/findPeopleByName/{firstName}| Finds a Person by an specific name      |
| Person         | PUT         | /api/person/v1                             | Updates a Person                        |
| Person         | PATCH       | /api/person/v1/{id}                        | Disable a specific person by id         |
| Person         | DELETE      | /api/person/v1/{id}                        | Deletes a Person                        |
| Books          | GET         | /api/books/v1/{id}                         | Get book by ID                          |
| Books          | GET         | /api/books/v1                              | Finds all books                         |
| Books          | POST        | /api/books/v1                              | Adds a new Book                         |
| Books          | DELETE      | /api/books/v1{id}                          | Deletes a book                          |
| Books          | PUT         | /api/books/v1                              | Updates a book                          |
| Authentication | POST        | /auth/signin                               | Returns all available parking spaces    |
| Authentication | PUT         | /auth/refresh/{username}                   | Returns a given parking space by ID     |
| File           | GET         | /api/file/v1/downloadFile/{filename:.+}    | Download files                          |
| File           | POST        | /api/file/v1/uploadMultipleFiles           | Upload multiple files                   |
| File           | POST        | /api/file/v1/uploadFile                    | Upload a file                           |
