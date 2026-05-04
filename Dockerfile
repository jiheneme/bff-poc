# Build avec la dernière version de Gradle pour supporter Shadow
FROM gradle:latest AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# On utilise 'build' qui génère le jar dans build/libs
RUN gradle build --no-daemon -x test

# Runtime léger
FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080

# On récupère le JAR généré (on utilise une wildcard pour être sûr du nom)
COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/bff-kotlin.jar

ENTRYPOINT ["java", "-jar", "/app/bff-kotlin.jar"]