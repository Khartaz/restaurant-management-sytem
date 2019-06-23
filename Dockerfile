FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/restaurant-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} restaurant.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/restaurant.jar"]
