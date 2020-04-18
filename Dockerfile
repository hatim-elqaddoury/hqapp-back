FROM openjdk:latest
EXPOSE 8080
ADD target/hq-docker.war hq-docker.war
ENTRYPOINT ["java", "war", "hq-docker.war"]