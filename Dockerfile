# 우분투 , jdk 설치
FROM openjdk:17.0.2-jdk
ENV APP_HOME=/apps
ARG JAR_FILE_PATH=build/libs/routemaster-0.0.1-SNAPSHOT.jar
WORKDIR $APP_HOME

RUN apt-get install -y apt-transport-https ca-certificates curl software-properties-common
RUN curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
RUN add-apt-repository "deb [arch=arm64] https://download.docker.com/linux/ubuntu focal stable"
RUN apt-get update -y && apt-get install -y docker-ce

COPY $JAR_FILE_PATH app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 