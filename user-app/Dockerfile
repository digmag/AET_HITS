FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/user-app-0.0.1-SNAPSHOT.jar /app/user-app-0.0.1-SNAPSHOT.jar
COPY /target/common-0.0.1-SNAPSHOT.jar /app/common-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/user-app-0.0.1-SNAPSHOT.jar"]