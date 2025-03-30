FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

COPY gradlew gradlew.bat ./
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

RUN ./gradlew dependencies --no-daemon

COPY src src

#RUN ./gradlew bootJar --no-daemon
RUN ./gradlew clean build --no-daemon --info # build 시 test 수행

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]