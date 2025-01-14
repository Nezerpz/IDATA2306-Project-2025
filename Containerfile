FROM docker.io/sapmachine:latest
WORKDIR /usr/share/java
COPY target/rentalroulette-0.0.1-SNAPSHOT.jar .
RUN java -jar /usr/share/java/rentalroulette-0.0.1-SNAPSHOT.jar
