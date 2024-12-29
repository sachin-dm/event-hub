# Stage 1: Build the application
FROM maven:3.8-openjdk-11 as build
WORKDIR /event-hub
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime image
FROM openjdk:11-jdk-slim
WORKDIR /event-hub
COPY --from=build /event-hub/target/event-hub.jar event-hub.jar
EXPOSE 8080
CMD ["java", "-jar", "event-hub.jar"]
