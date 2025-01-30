# Use a multi-stage build for efficiency
FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only necessary files for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src
RUN mvn clean package -DskipTests

# Use a compatible OpenJDK runtime (non-alpine)
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/kakeibo-bills-parser-1.0.0.jar app.jar

# Expose application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]