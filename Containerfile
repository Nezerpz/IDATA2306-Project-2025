IMAGE sapmachine:latest
COPY target/*.jar /usr/share/java/
RUN java -jar /usr/share/java/*.jar
