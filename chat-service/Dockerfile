# --- Этап 1: Сборка приложения (с кэшем Gradle и зеркалом Maven) ---
FROM eclipse-temurin:23-jdk-alpine AS builder
WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

# Перенаправляем Maven Central на зеркало Google, чтобы обойти 429
RUN mkdir -p /root/.gradle/init.d && \
    cat > /root/.gradle/init.d/google-mirror.gradle <<'EOF'
def mirror = 'https://maven-central.storage-download.googleapis.com/maven2/'
def centralHosts = ['repo.maven.apache.org', 'repo1.maven.org']

def useMirror = { RepositoryHandler repos ->
    repos.withType(MavenArtifactRepository).configureEach { r ->
        if (r.url?.host in centralHosts) { r.url = mirror }
    }
    if (repos.findByName('CentralMirror') == null) {
        def m = repos.maven { url = mirror; name = 'CentralMirror' }
        repos.remove(m)
        repos.addFirst(m)
    }
}

beforeSettings { settings ->
    useMirror(settings.pluginManagement.repositories)
    settings.pluginManagement.repositories.gradlePluginPortal()
}

settingsEvaluated { settings ->
    useMirror(settings.pluginManagement.repositories)
    if (!settings.dependencyResolutionManagement.repositories.isEmpty()) {
        useMirror(settings.dependencyResolutionManagement.repositories)
    }
}

allprojects {
    afterEvaluate {
        if (!buildscript.repositories.isEmpty()) { useMirror(buildscript.repositories) }
        if (!repositories.isEmpty()) { useMirror(repositories) }
    }
}
EOF

RUN --mount=type=cache,target=/root/.gradle \
    chmod +x gradlew && ./gradlew dependencies --no-daemon

COPY src ./src
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew clean build -x test --no-daemon

# --- Этап 2: Анализ зависимостей и сборка кастомного JRE ---
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

# --- Этап 3: Финальный образ (только JRE + jar) ---
FROM alpine:3.20
WORKDIR /app

COPY --from=jre-builder /custom-jre /opt/jre
ENV PATH="/opt/jre/bin:$PATH"

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]