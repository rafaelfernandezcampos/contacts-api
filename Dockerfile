FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:20
WORKDIR /app
COPY target/contacts-api-1.0-SNAPSHOT.jar /app/contacts-api.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "contacts-api.jar"]
