# ETAPA 1: Construcción
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ETAPA 2: Imagen de Producción
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=build /app/target/chat-0.0.1-SNAPSHOT.jar /app/api-v1.jar

RUN chown spring:spring /app/api-v1.jar
USER spring:spring

EXPOSE 8080

ENV JAVA_OPTS="-Xmx512m -Xms256m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/api-v1.jar"]