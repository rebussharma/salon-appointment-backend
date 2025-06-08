# 1. Official Maven JDK 17 image is used
# As build is a declaration that'll help reference build later
FROM maven:3.9.6-eclipse-temurin-17 AS build

# 2. Set working directory inside the image
# All following commands will be run from here.
WORKDIR /app

# 3. project file pom.xml and source code needs to be copied into a container
# to build jar
COPY pom.xml .
COPY src ./src

# 4. Runs the Maven build, creating a JAR in the target/ directory.
# -DskipTests is used to avoid running tests, faster build process
RUN mvn clean package -DskipTests

# 5. Starts a new stage using a lighter image for production.
# Alpine Linux and includes JDK 17 only (no Maven), smaller size image
FROM eclipse-temurin:17-jdk-alpine

# 6. Set working directory for the runtime container, sets the working directory in the second stage.
WORKDIR /app

# 7. Copies the built JAR file from the previous build stage.
# This avoids including all the source code and Maven cache in the final image.
COPY --from=build /app/target/*.jar app.jar

# Defines the command to run when the container starts. Run the app
# Launches the Spring Boot application.
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080