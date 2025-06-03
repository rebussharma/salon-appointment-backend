# 1. Use Maven with JDK 17 to build the application
# This starts a multi-stage build.

# It uses an official Maven image with JDK 17.

# Naming this stage as build allows us to reference it later.
FROM maven:3.9.6-eclipse-temurin-17 AS build

# 2. Set working directory inside the image
# All following commands will be run from here.
WORKDIR /app

# 3. Copy the Maven project files to the image (pom.xml and source code)
# Copies pom.xml and source code (src/) into the container.

# Needed to build the Spring Boot JAR using Maven.
COPY pom.xml .
COPY src ./src

# 4. Run Maven package to build the Spring Boot app
# Runs the Maven build, creating a JAR in the target/ directory.

# -DskipTests avoids running tests (optional, speeds up builds).
RUN mvn clean package -DskipTests

# 5. Use a smaller runtime image with just JDK 17
# Starts a new stage using a lighter image for production.

# Based on Alpine Linux and includes JDK 17 only (no Maven).

# Reduces image size significantly.
FROM eclipse-temurin:17-jdk-alpine

# 6. Set working directory for the runtime container, sets the working directory in the second stage.
WORKDIR /app

# 7. Copy the JAR file from the build stage
# Copies the built JAR file from the previous build stage.

# This avoids including all the source code and Maven cache in the final image.
COPY --from=build /app/target/*.jar app.jar

# 8. Run the Spring Boot app
# Defines the command to run when the container starts.

# Launches the Spring Boot application.
ENTRYPOINT ["java", "-jar", "app.jar"]

# Set default environment variables (these will be overridden when container starts)
ENV DB_URL=jdbc:postgresql://pityingly-proud-camel.data-1.use1.tembo.io:5432/postgres
ENV DB_USERNAME=postgres
# Password should be passed at runtime and not included in the image
ENV DB_PASSWORD=

EXPOSE 8080