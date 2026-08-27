FROM eclipse-temurin:21-jdk-jammy AS deps
WORKDIR /build
COPY --chmod=0755 mvnw mvnw
COPY .mvn .mvn/
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

FROM deps AS package
WORKDIR /build
COPY src src/
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/auth-service-0.0.1-SNAPSHOT.jar target/app.jar

FROM package AS extract
WORKDIR /build
RUN java -Djarmode=tools -jar target/app.jar extract --layers --launcher --destination target/extracted


FROM eclipse-temurin:21-jre-jammy AS final
WORKDIR /app
COPY --from=extract /build/target/extracted/dependencies/ ./
COPY --from=extract /build/target/extracted/spring-boot-loader/ ./
COPY --from=extract /build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract /build/target/extracted/application/ ./
EXPOSE 8001
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]