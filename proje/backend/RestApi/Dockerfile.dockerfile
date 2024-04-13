FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean install

FROM openjdk:latest
WORKDIR /app
COPY --from=build app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]

