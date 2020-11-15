FROM openjdk:8-jdk

RUN mkdir /home/api

COPY /api/target/api-0.0.1-SNAPSHOT.jar  /home/api

CMD ["java","-jar","/home/api/api-0.0.1-SNAPSHOT.jar"]

