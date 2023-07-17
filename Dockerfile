FROM openjdk:20
WORKDIR /app
COPY target/contacts-api-1.0-SNAPSHOT.jar /app/contacts-api.jar
ENTRYPOINT ["java", "-jar", "contacts-api.jar"]
EXPOSE 8000