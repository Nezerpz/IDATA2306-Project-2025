# Both container stages need this argument
ARG BUILD_DIR=/tmp/rentalroulette_build
# Mentioning it after FROM includes it as defined here


# Build-stage
FROM docker.io/maven:latest AS build
ARG BUILD_DIR
RUN mkdir -p ${BUILD_DIR}
ADD . ${BUILD_DIR}
WORKDIR ${BUILD_DIR}
# Skip tests here as db is not running yet
RUN mvn clean package -DskipTests


# Package stage
FROM docker.io/sapmachine:latest
ARG BUILD_DIR
ARG APPDIR=/usr/share/java
WORKDIR ${APPDIR}
COPY --from=build ${BUILD_DIR}/target/*.jar .
COPY --from=build ${BUILD_DIR}/user-uploads ./user-uploads
CMD java -jar rentalroulette-0.0.1-SNAPSHOT.jar
