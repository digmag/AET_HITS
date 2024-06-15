FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/doc-core-0.0.1-SNAPSHOT.jar /app/doc-core-0.0.1-SNAPSHOT.jar
COPY /target/common-0.0.1-SNAPSHOT.jar /app/common-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/doc-core-0.0.1-SNAPSHOT.jar"]