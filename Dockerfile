# 우분투 , jdk 설치
FROM arm64v8/ubuntu
FROM openjdk:17.0.2-jdk
ENV APP_HOME=/apps
ARG JAR_FILE_PATH=build/libs/routemaster-0.0.1-SNAPSHOT.jar
WORKDIR $APP_HOME

COPY $JAR_FILE_PATH app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 