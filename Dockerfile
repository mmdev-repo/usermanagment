FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY mmdevrepo/mm-docker-repo/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]