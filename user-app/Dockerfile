FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn clean package

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/user-app-0.0.1-SNAPSHOT.jar /app/user-app-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/common/target/common-0.0.1-SNAPSHOT.jar /app/common-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/user-app-0.0.1-SNAPSHOT.jar"]