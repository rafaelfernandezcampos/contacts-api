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