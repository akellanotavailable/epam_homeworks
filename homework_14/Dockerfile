FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /root/app
COPY target/'demo-0.0.1-SNAPSHOT.jar' /root/app/homework.jar

EXPOSE 8080

CMD java $JAVA_OPTS -jar homework.jar
