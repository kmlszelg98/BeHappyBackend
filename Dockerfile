FROM openjdk:8
COPY if-behappy-backend-1.0-SNAPSHOT.jar /if-behappy-backend-1.0.jar
COPY run.sh /run.sh
EXPOSE 8080
WORKDIR /
ENV JAVA_OPTS=""
ENTRYPOINT [ "/run.sh" ]
