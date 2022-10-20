FROM eclipse-temurin:17-jdk-alpine
MAINTAINER MCSDevTeam
COPY target/MCSAnalyser-0.0.1-SNAPSHOT.jar MCSAnalyser-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "MCSAnalyser-0.0.1-SNAPSHOT.jar"]