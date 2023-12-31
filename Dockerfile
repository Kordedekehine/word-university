FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package  -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/cbt-app-0.0.1-SNAPSHOT.jar cbt-app.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","cbt-app.jar"]

