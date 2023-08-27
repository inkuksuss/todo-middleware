#FROM gradle:8.1.1-jdk17-alpine as builder
#WORKDIR /build
#
#COPY build.gradle settings.gradle /build/
#RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true
#
#COPY . /build
#RUN gradle build -x test --parallel
#
#FROM openjdk:17-slim
#COPY --from=builder /build/build/libs/*.jar ./app.jar
#ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]
#
FROM openjdk:17-jdk
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]