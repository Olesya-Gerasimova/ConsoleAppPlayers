FROM eclipse-temurin:8u422-b05-jre-noble
COPY target/WebApp.jar /WebApp.jar
# This is the port that your javalin application will listen on
EXPOSE 7070
ENTRYPOINT ["java", "-jar", "/WebApp.jar"]