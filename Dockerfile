FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8888
ARG CERT=build/libs/keystore.p12
ARG JAR_FILE=build/libs/company-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} restaurant.jar
ADD ${CERT} keystore.p12
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/restaurant.jar"]