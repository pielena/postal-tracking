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
- PostgreSQL, Spring JPA
- Maven
- Docker (for TestContainers)
- Flyway 
- Open API (Swagger)

### Getting started:

There are two ways to run the app: you can get .war and deploy it 
to your server, or you can work with .jar

#### Common steps:
- Clone the project from GitHub
- You need to specify the correct database settings in application.yml

#### Getting started with .war :
- In the pom.xml file you need to activate two sections: line 15 with 
the packaging type and spring-boot-starter-tomcat dependency
- Use maven to build .war file of the project
- deploy .war to your server

#### Getting started with .jar :
- The best way to run the project is with IDE like IntelliJ IDEA.

### Usage:

- In the /resources/db/sql folder you can find simple init.sql script 
for your DB
- In the /resources folder you can find "Postal Tracking API.postman_collection.json". 
You can use it for testing.
- You can also see all available endpoints by visiting 
http://localhost:8080/app/swagger-ui/index.htm (change prefix if necessary)
- You can also find the file "test-report-2023-09-03.png" in the /resource 
folder to see the test coverage report

### Contacts:

- Author: Pilyugina Elena
- Gmail: elena.pilyugina.job@gmail.com
- https://github.com/pielena