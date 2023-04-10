FROM openjdk:8-alpine
WORKDIR opt/app

COPY /build/libs/usermanager-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]