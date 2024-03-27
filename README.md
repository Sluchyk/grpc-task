### Bookstore Inventory Management System Setup Guide

This guide provides step-by-step instructions to set up and run the Bookstore Inventory Management System, a SpringBoot application utilizing gRPC with Protobuf for communication, Postgresql for the database, and other technologies such as Docker, Testcontainers, Project Reactor, and more.

Now, let's proceed with the setup instructions:

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/Sluchyk/grpc-task.git
    ```

2. **Build the Project**:
   Navigate to the project directory and run the following command to build the project. Tests are excluded to speed up the process:
    ```bash
    ./gradlew build -x test
    ```

3. **Start Postgresql Container**:
   If `./gradlew build` command was successful, start a Postgresql container with the following configuration:
    - Database Name: `testdb`
    - Username: `test`
    - Password: `test`

4. **Run Docker Compose**:
   Execute the following command to run Docker Compose, which will start the necessary containers:
    ```bash
    docker-compose up
    ```

5. **Access Swagger Documentation**:
   Once the containers are up and running, you can access the Swagger documentation for API usage by opening the following link in your browser:
    ```
    http://localhost:8080/webjars/swagger-ui/index.html
    ```

6. **Interacting with the Project**:
   Utilize the provided documentation in Swagger UI to make requests and interact with the project as needed.

### Notes

- Ensure that no other services are utilizing ports `8080` and `5432`, as these ports are used by the application and the Postgresql database respectively.
- Make sure Docker is running and accessible on your system before executing Docker-related commands.
- If you encounter any issues during the setup or execution, refer to the project's documentation or seek assistance from the project maintainers.

Follow these steps carefully to set up and run the Bookstore Inventory Management System successfully.