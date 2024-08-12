FROM openjdk:17-ea-10-alpine3.13
COPY target/WebApp.jar /WebApp.jar
# This is the port that your javalin application will listen on
EXPOSE 7070
ENTRYPOINT ["java", "-jar", "/WebApp.jar"]