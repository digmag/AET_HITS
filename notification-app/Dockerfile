FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY notification-app-0.0.1-SNAPSHOT.jar /app/notification-app-0.0.1-SNAPSHOT.jar
COPY common-0.0.1-SNAPSHOT.jar /app/common-0.0.1-SNAPSHOT.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app/notification-app-0.0.1-SNAPSHOT.jar"]