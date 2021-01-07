FROM openjdk:8-jdk-alpine

MAINTAINER Mainlevel Consulting AG

VOLUME /logs

ADD @project.build.finalName@.jar app.jar
RUN apk add --update bash && rm -rf /var/cache/apk/* && bash -c 'touch /app.jar'

ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=container,dev -jar /app.jar