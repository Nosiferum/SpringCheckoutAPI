FROM maven:3.9.4-eclipse-temurin-17 AS build

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine

WORKDIR /springcheckout-api
COPY --from=build ./target/SpringCheckoutAPI-0.0.1-SNAPSHOT.jar SpringCheckoutAPI-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar", "SpringCheckoutAPI-0.0.1-SNAPSHOT.jar"]