# ===== Etapa 1: Compilar con Maven =====
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

# ===== Etapa 2: Ejecutar en Tomcat =====
FROM tomcat:9.0-jdk21

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/SistemaBodega.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]