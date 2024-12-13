FROM eclipse-temurin:17-alpine
COPY /target/customer-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]