FROM openjdk:11-jre-slim
EXPOSE 8089
WORKDIR /app
RUN apt-get update && apt-get install -y curl
RUN curl -o eventsProject-1.0.jar -L "http://192.168.162.222:8081/repository/maven-releases/tn/esprit/eventsProject/1.0/eventsProject-1.0.jar"
ENTRYPOINT ["java","-jar","eventsProject-1.0.jar"]
