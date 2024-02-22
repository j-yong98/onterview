FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /onterview-storage.jar
ENTRYPOINT ["java","-jar","/onterview-storage.jar"]

# ffmpeg 설치 명령어 추가
RUN apk add --no-cache ffmpeg
