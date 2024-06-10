FROM maven:3.8.4-openjdk-17-slim as builder
WORKDIR /app
COPY . /app/common/.
RUN mvn -f /app/common/pom.xml clean install