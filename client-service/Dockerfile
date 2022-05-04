FROM openjdk:11

EXPOSE 8080

ADD ./target/client-service-0.0.1-SNAPSHOT.jar /client-service.jar

ENTRYPOINT ["java", "-jar", "/client-service.jar"]