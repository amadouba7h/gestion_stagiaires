FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/encadreur-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9092

ENTRYPOINT ["java", "-jar", "app.jar"]
