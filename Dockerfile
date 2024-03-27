FROM openjdk:17-jdk-slim
COPY build/libs/grpc-task-0.0.1-SNAPSHOT.jar  grpc-task.jar
ENTRYPOINT ["java","-jar","grpc-task.jar"]