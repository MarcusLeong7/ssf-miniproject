FROM openjdk:23-jdk-oracle AS builder

ARG COMPILE_DIR=/compiledir

WORKDIR ${COMPILE_DIR}

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true

# App will run in second stage
# ENTRYPOINT java -jar target/day18-0.0.1-SNAPSHOT.jar

# Second stage
FROM openjdk:23-jdk-oracle

ARG WORK_DIR=/app

WORKDIR ${WORK_DIR}

COPY --from=builder /compiledir/target/noticeboard-0.0.1-SNAPSHOT.jar noticeboardApp.jar

ENV PORT=8080

ENV SPRING_DATA_REDIS_HOST=localhost SPRING_DATA_REDIS_PORT=6379
ENV SPRING_DATA_REDIS_USERNAME="" SPRING_DATA_REDIS_PASSWORD=""

ENV MY_API_KEY=""
ENV SPRING_SECURITY_USER_NAME="" SPRING_SECURITY_USER_PASSWORD=""

EXPOSE ${PORT}

HEALTHCHECK --interval=60s --timeout=5s --start-period=120s --retries=3 \
   CMD curl -s -f http://localhost:${PORT}/health || exit 1

ENTRYPOINT SERVER_PORT=${PORT} java -jar noticeboardApp.jar