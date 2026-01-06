# Unpack application into layers
FROM eclipse-temurin:21-jre as builder
COPY target/devTool.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# Install application into layers
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=builder dependencies/ .
COPY --from=builder snapshot-dependencies/ .
COPY --from=builder spring-boot-loader/ .
COPY --from=builder application/ .

# Launch application
ENTRYPOINT ["java", "--add-opens", "java.base/java.util=ALL-UNNAMED", "org.springframework.boot.loader.launch.JarLauncher"]