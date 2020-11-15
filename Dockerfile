FROM openjdk:8-jdk

COPY . .

RUN apt update -y && \
    apt install -y maven && \
    mvn clean install && \
    mkdir /home/api

COPY /api/target/api-0.0.1-SNAPSHOT.jar  /home/api

CMD ["java","-jar","/home/api/api-0.0.1-SNAPSHOT.jar"]

