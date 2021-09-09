FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /target/User-Microservice-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENV JAVA_OPTS=""
RUN sh -c "touch User-Microservice-0.0.1-SNAPSHOT.jar"
ENTRYPOINT [ "java", "-jar", "User-Microservice-0.0.1-SNAPSHOT.jar" ]
