# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the executable jar from the build stage
COPY --from=build /app/target/transaction-service-0.0.1-SNAPSHOT.jar ./transaction-service.jar
# Expose the port the application runs on
EXPOSE 8082
# Command to run the application
ENTRYPOINT ["java", "-jar", "transaction-service.jar"]
