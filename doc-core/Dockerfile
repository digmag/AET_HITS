FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY doc-core-0.0.1-SNAPSHOT.jar /app/doc-core-0.0.1-SNAPSHOT.jar
COPY common-0.0.1-SNAPSHOT.jar /app/common-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/doc-core-0.0.1-SNAPSHOT.jar"]