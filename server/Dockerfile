# Stage 1: Build stage
FROM alpine:latest AS builder
RUN apk add --no-cache openjdk21

# Set JAVA_HOME environment variable
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Set working directory
WORKDIR /app

# Copy Maven wrapper files
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn/

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime stage
FROM alpine:latest

# Install only OpenJDK 21
RUN apk add --no-cache openjdk21

# Set JAVA_HOME for JRE
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Set working directory
WORKDIR /app

# Copy only the JAR file from build stage
COPY --from=builder /app/target/dmw-0.0.1-SNAPSHOT.jar app.jar

# Expose port for Spring Boot
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
