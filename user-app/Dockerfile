FROM maven:3.8.4-openjdk-17-slim as build
WORKDIR /app
COPY . /app/user-app/.
RUN mvn clean package

FROM openjdk:17-jdk-alpine
COPY target/user-app-0.0.1-SNAPSHOT.jar /app/user-app-0.0.1-SNAPSHOT.jar
COPY /target/common-0.0.1-SNAPSHOT.jar /app/common-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/user-app-0.0.1-SNAPSHOT.jar"]