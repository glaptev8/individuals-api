FROM openjdk:21-jdk-slim-buster
WORKDIR /individuals

COPY build/libs/individuals-api-1.0-SNAPSHOT.jar /individuals/build/

WORKDIR /individuals/build

EXPOSE 8082

ENTRYPOINT java -jar individuals-api-1.0-SNAPSHOT.jar