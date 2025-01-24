# Use an official OpenJDK runtime as a parent image
FROM openjdk:23-jdk-slim as builder

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and Gradle files
COPY gradle gradle
COPY build.gradle settings.gradle gradle-wrapper.properties ./

# Download dependencies (this helps to cache dependencies and improve build time)
RUN ./gradlew build --no-daemon

# Copy the rest of your application code
COPY src ./src

# Build the app with Gradle
RUN ./gradlew bootJar --no-daemon

# Create a second stage to reduce the image size
FROM openjdk:23-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]

# Expose port 8080 (default for Spring Boot)
EXPOSE 8080
