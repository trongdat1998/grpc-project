FROM openjdk:11

EXPOSE 9001

ADD ./target/grpc-serviceB-0.0.1-SNAPSHOT.jar /grpc-serviceB.jar

ENTRYPOINT ["java", "-jar", "/grpc-serviceB.jar"]