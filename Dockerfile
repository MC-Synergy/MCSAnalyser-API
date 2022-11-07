FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY ./.mvn ./.mvn
COPY ./mvnw ./mvnw
COPY ./pom.xml ./pom.xml
RUN ./mvnw dependency:resolve
COPY ./src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jdk-alpine as production
LABEL "MAINTAINER"="MCSDevTeam" 
COPY --from=builder /app/target/MCSAnalyser-0.0.1-SNAPSHOT.jar MCSAnalyser-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "MCSAnalyser-0.0.1-SNAPSHOT.jar"]



