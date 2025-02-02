FROM gradle:8.12.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:17
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/server/build/libs/*.jar /app/timetowish-server.jar
ENTRYPOINT ["java","-jar","/app/timetowish-server.jar"]