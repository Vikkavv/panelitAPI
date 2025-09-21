# =========================
# 1) Compilation fase
# =========================
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Create app work directory
WORKDIR /app

# Copy pom.xml and download dependencies (cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the font code and compile
COPY src ./src
RUN mvn clean package -DskipTests

# =========================
# 2) Execution fase
# =========================
FROM eclipse-temurin:21-jdk-jammy

# Work directory
WORKDIR /app

# Copy the generated jar by the previous fase
COPY --from=build /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]