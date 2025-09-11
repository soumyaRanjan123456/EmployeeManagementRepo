FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/crud-practice-0.0.1-SNAPSHOT.jar myapp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "myapp.jar"]
