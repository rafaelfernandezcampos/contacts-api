FROM maven:3.6.3 AS maven
LABEL MAINTAINER="rafaelstz00@gmail.com"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package 

# Fetching latest version of Java
FROM openjdk:18
 
# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY ./target/contacts-api-1.0-SNAPSHOT.jar /app

# Exposing port 8000
EXPOSE 8000

# Starting the application
CMD ["java", "-jar", "contacts-api-1.0-SNAPSHOT.jar"]
