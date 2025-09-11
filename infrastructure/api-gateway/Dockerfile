# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy the executable jar from the build stage
COPY --from=build /app/target/api-gateway-0.0.1-SNAPSHOT.jar ./api-gateway.jar
# Expose the port the application runs on
EXPOSE 8080
# Command to run the application
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
