FROM eclipse-temurin:17

LABEL mentainer="thaipeiidev@gmail.com"

WORKDIR /app

COPY /target/website-0.0.1-SNAPSHOT.jar /app/website-cafe.jar

ENTRYPOINT ["java", "-jar", "website-cafe.jar"]