FROM amazoncorretto:17

ADD target/daily-helper-api-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]