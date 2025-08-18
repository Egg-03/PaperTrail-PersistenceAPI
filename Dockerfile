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

RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final Docker image using OpenJDK 21
FROM openjdk:21-jdk
WORKDIR /app
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /app/target/persistence-0.0.1-SNAPSHOT.jar persistence-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","persistence-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]
EXPOSE 8081