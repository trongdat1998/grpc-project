FROM openjdk:11

EXPOSE 9000

ADD ./target/grpc-serviceA-0.0.1-SNAPSHOT.jar /grpc-serviceA.jar

ENTRYPOINT ["java", "-jar", "/grpc-serviceA.jar"]