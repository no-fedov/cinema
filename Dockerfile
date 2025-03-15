FROM openjdk:17
WORKDIR /app
COPY ./target/cinema-0.0.1-SNAPSHOT.jar cinema.jar
CMD ["java",  "-jar", "cinema.jar"]