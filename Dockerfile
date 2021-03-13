FROM openjdk:8-jdk-alpine

MAINTAINER sudhirkumar

ARG JAR_FILE=target/football-1.0.0.0.jar

WORKDIR /opt/app

COPY ${JAR_FILE} football-1.0.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","football-1.0.0.0.jar"]
