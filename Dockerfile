FROM openjdk:8-alpine

COPY target/*.jar /usr/lightning/app.jar

WORKDIR /usr/lightning

CMD ["java", "-jar", "app.jar"]