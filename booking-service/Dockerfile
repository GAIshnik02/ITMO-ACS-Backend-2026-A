FROM eclipse-temurin:23-jdk-alpine AS builder
WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
RUN --mount=type=cache,target=/root/.gradle \
    chmod +x gradlew && ./gradlew dependencies --no-daemon

COPY src ./src
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:23-jdk-alpine AS jre-builder
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

RUN jdeps --multi-release 23 --print-module-deps --ignore-missing-deps app.jar > modules.txt

RUN jlink \
    --add-modules $(cat modules.txt) \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /custom-jre

FROM alpine:3.20
WORKDIR /app

COPY --from=jre-builder /custom-jre /opt/jre
ENV PATH="/opt/jre/bin:$PATH"

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]