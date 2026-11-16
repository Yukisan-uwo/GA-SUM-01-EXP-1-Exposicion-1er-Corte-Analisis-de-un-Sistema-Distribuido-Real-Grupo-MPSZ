FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /src
COPY pom.xml .
COPY src ./src
RUN mvn -B -ntp package dependency:copy-dependencies

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /src/target/classes ./classes
COPY --from=build /src/target/dependency ./lib
ENTRYPOINT ["java", "-cp", "/app/classes:/app/lib/*", "ec.uteq.sgra.PracticaJms"]
