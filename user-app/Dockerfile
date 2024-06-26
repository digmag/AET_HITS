FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY user-app-0.0.1-SNAPSHOT.jar /app/user-app-0.0.1-SNAPSHOT.jar
COPY common-0.0.1-SNAPSHOT.jar /app/common-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/user-app-0.0.1-SNAPSHOT.jar"]