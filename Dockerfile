# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged jar file into the Docker image
COPY target/adminwork-0.0.1-snapshot.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8087

# Set the entry point to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

ENV SPRING_PROFILES_ACTIVE=test
