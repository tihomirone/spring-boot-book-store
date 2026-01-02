# Use Eclipse Temurin (successor to OpenJDK) as a parent image
FROM eclipse-temurin:23-jdk-alpine AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and Gradle files
COPY gradle gradle
COPY build.gradle settings.gradle gradlew gradle/wrapper/gradle-wrapper.properties .env ./

# Copy the rest of your application code
COPY src ./src

RUN chmod +x ./gradlew

# Download dependencies (this helps to cache dependencies and improve build time)
RUN ./gradlew build -P prod -x test --no-daemon

# Build the app with Gradle
RUN ./gradlew bootJar --no-daemon

# Create a second stage to reduce the image size
FROM eclipse-temurin:23-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]

# Expose port 8080 (default for Spring Boot)
EXPOSE 8080
