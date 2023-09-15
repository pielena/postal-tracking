# Postal Tracking

REST API, который позволяет отслеживать почтовые отправления.
В приложении реализована возможность регистрировать почтовые отправления, 
их передвижение между почтовыми отделениями, а также возможность получения 
информации и всей истории передвижения конкретного почтового отправления.

REST API that allows you to track postal items.
The application provides the ability to register postal items,
their movements between post offices, as well as the ability to get the 
information and the entire history of movements of a certain postal item.

### Features:

- Java 17
- Spring Boot 3.1.2
- PostgreSQL, Spring Data JPA
- Maven
- Docker (for TestContainers)
- Flyway 
- Open API (Swagger)

### Getting started:

- Clone the project from GitHub
- You need to specify the correct database settings in application.yml
- The best way to run the project is with IDE like IntelliJ IDEA.

### Usage:

- In the /develop/sql folder you can find simple init.sql script 
for your DB
- In the /develop folder you can find "Postal Tracking API.postman_collection.json". 
You can use it for testing.
- You can also see all available endpoints by visiting 
http://localhost:8080/app/swagger-ui/index.htm (change prefix if necessary)

### Contacts:

- Author: Pilyugina Elena
- Gmail: elena.pilyugina.job@gmail.com
- https://github.com/pielena