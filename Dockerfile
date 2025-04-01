# Importing JDK and copying required files
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src src

# Copy Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final Docker image using OpenJDK 19
FROM openjdk:17.0.1-jdk-slim
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080