FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY C:/Users/Mariano/Desktop/Mariano/eclipse-workspace/managment/target/managment-0.0.1 app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]