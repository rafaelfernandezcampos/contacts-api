FROM maven:3.6.3 AS maven
LABEL MAINTAINER="rafaelstz00@gmail.com"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package

# Second stage: Fetching latest version of Java and setting up the application
FROM openjdk:18

# Setting up work directory
WORKDIR /app

# Copy the jar file from the first stage into our app
COPY --from=maven /usr/src/app/target/contacts-api-1.0-SNAPSHOT.jar /app

# Expose port 8000
EXPOSE 8000

# Start the application
CMD ["java", "-jar", "contacts-api-1.0-SNAPSHOT.jar"]
