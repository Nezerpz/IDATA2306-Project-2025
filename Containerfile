FROM docker.io/sapmachine:latest
WORKDIR /usr/share/java
COPY target/rentalroulette-0.0.1-SNAPSHOT.jar .
CMD java -jar /usr/share/java/rentalroulette-0.0.1-SNAPSHOT.jar
