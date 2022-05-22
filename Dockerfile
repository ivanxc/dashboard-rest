FROM openjdk:11
EXPOSE 8080
WORKDIR /app
COPY ./target/dashboard-rest-0.0.1-SNAPSHOT.jar .
CMD java -jar dashboard-rest-0.0.1-SNAPSHOT.jar