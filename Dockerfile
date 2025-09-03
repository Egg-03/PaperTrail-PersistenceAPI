# Importing JDK and copying required files
FROM openjdk:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

# Copy the source files after dependencies are cached
COPY src ./src

RUN ./mvnw clean package

# Stage 2: Create the final Docker image using IBM Semeru Runtime
FROM ibm-semeru-runtimes:open-21-jre-focal AS runtime
WORKDIR /app
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /app/target/persistence.jar persistence.jar
ENTRYPOINT ["java", "-Xgcpolicy=metronome", "-Xgc:targetUtilization=80", "-Xgc:targetPauseTime=10", "-Xtune:virtualized", "-XX:+IdleTuningGcOnIdle", "-jar", "/app/persistence.jar", "--spring.profiles.active=prod"]
EXPOSE 8081