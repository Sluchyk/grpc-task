
Running Instructions for the Bookstore Inventory Management System
This guide provides step-by-step instructions to set up and run the Bookstore Inventory Management System, a SpringBoot application utilizing gRPC with Protobuf for communication, Postgresql for the database, and other technologies such as Docker, Testcontainers, Project Reactor, and more.

Prerequisites
Before proceeding, ensure you have the following prerequisites installed and configured on your system:

Git
Docker
Java Development Kit (JDK)
Gradle
Steps to Run the Application
Clone the Repository:

bash
Copy code
git clone https://github.com/Sluchyk/grpc-task.git
Build the Project:
Navigate to the project directory and run the following command to build the project. Tests are excluded to speed up the process:

bash
Copy code
./gradlew build -x test
Start Postgresql Container:
If want to use command ./gradlew then start a Postgresql container with the following configuration:

Database Name: testdb
Username: test
Password: test
Run Docker Compose:
Execute the following command to run Docker Compose, which will start the necessary containers:

Copy code
docker-compose up
Access Swagger Documentation:
Once the containers are up and running, you can access the Swagger documentation for API usage by opening the following link in your browser:

bash
Copy code
http://localhost:8080/webjars/swagger-ui/index.html
Interacting with the Project:
Utilize the provided documentation in Swagger UI to make requests and interact with the project as needed.

Notes
Ensure that no other services are utilizing ports 8080 and 5432, as these ports are used by the application and the Postgresql database respectively.
Make sure Docker is running and accessible on your system before executing Docker-related commands.
If you encounter any issues during the setup or execution, refer to the project's documentation or seek assistance from the project maintainers.
Follow these steps carefully to set up and run the Bookstore Inventory Management System successfully.
