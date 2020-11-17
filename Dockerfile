# Build stage
FROM hseeberger/scala-sbt:8u265_1.4.2_2.13.3 AS build

WORKDIR /root/

COPY ./ ./

RUN sbt assembly

ADD target/scala-2.13/app.jar app.jar

# Run stage
FROM openjdk:8-jre-alpine3.9

COPY --from=build /root/app.jar /app.jar

RUN chmod +x /app.jar

EXPOSE 8080

CMD ["java", "-Dserver.port=8080", "-jar", "app.jar"]