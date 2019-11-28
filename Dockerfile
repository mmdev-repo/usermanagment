FROM openjdk:8-jdk-alpine
EXPOSE 8090
ADD app.jar managment-0.0.1.jar
ENTRYPOINT ["java","-jar","managment-0.0.1.jar"]