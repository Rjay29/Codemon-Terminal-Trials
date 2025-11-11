# Multi-stage Dockerfile for Codemon (Java 21)
# Builder: compile and copy dependencies
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Copy only pom first to leverage Docker layer caching
COPY pom.xml ./
COPY src ./src

# Build the project and copy dependencies to target/dependency
RUN mvn -B -DskipTests package dependency:copy-dependencies

# Runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy application jar and dependencies from builder stage
COPY --from=builder /workspace/target/PokeAPI-1.0.jar ./app.jar
COPY --from=builder /workspace/target/dependency ./lib

# Set UTF-8 encoding and prepare for both interactive and non-interactive modes
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Use classpath that includes the jar and all dependency jars
# Arguments passed to docker run will be passed to the app
ENTRYPOINT ["java", "-cp", "app.jar:lib/*", "Codemon.PKMApp"]
