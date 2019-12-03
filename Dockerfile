# STAGE: Build
FROM gradle:5.6.4-jdk8 as builder

# Create Working Directory
ENV BUILD_DIR=/home/gradle/src
WORKDIR $BUILD_DIR
COPY --chown=gradle:gradle . ${BUILD_DIR}

# Build Jar
RUN gradle build

# STAGE: Deploy
FROM openjdk:8-jre

# Create app directory, chgrp, and chmod
ENV APP_HOME=/app
WORKDIR $APP_HOME
RUN chgrp -R 0 $APP_HOME && chmod -R g=u $APP_HOME

# Copy jar file over from builder stage
COPY --from=builder /home/gradle/src/build/libs/refapp-0.0.1-SNAPSHOT.jar $APP_HOME
RUN mv ./refapp-0.0.1-SNAPSHOT.jar app.jar

USER 2000

ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT exec java ${JAVA_OPTS} -jar app.jar