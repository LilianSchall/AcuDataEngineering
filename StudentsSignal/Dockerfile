# This Dockerfile has two required ARGs to determine which base image
# to use for the JDK and which sbt version to install.

ARG OPENJDK_TAG=17-jdk-slim
FROM openjdk:${OPENJDK_TAG} as build

ARG SBT_VERSION=1.10.0

# prevent this error: java.lang.IllegalStateException: cannot run sbt from root directory without -Dsbt.rootdir=true; see sbt/sbt#1458
WORKDIR /app

RUN apt-get update && apt-get install -y curl

# Install sbt
RUN \
  mkdir /working/ && \
  cd /working/ && \
  curl -L -o sbt-$SBT_VERSION.deb https://repo.scala-sbt.org/scalasbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  cd && \
  rm -r /working/ && \
  sbt sbtVersion

COPY . /app

RUN sbt clean assembly

ARG OPENJDK_TAG=17-jdk-slim
FROM openjdk:${OPENJDK_TAG}

WORKDIR /app

COPY --from=build /app/target/scala-2.13/StudentsSignal-assembly-0.1.0-SNAPSHOT.jar /app/app.jar
COPY ./src/main/resources /app/src/main/resources
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]
