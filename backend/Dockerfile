FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} onterview.jar
ENTRYPOINT ["java","-jar","/onterview.jar"]