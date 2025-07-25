FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /application

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src/ ./src

# COPY init/ ./init
COPY .openapi-generator-ignore .

RUN mvn package && cp target/*.jar application.jar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /application

COPY --from=builder application/application.jar .
EXPOSE 3001
CMD ["java", "-jar", "application.jar"]