FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn clean package

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/notification-app-0.0.1-SNAPSHOT.jar /app/notification-app-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/common/target/common-0.0.1-SNAPSHOT.jar /app/common-0.0.1-SNAPSHOT.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app/notification-app-0.0.1-SNAPSHOT.jar"]