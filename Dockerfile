FROM hseeberger/scala-sbt:8u265_1.4.2_2.13.3 AS build

COPY ./ ./

#RUN sbt assembly

#WORKDIR root

#ADD target/scala-2.13/app.jar app.jar

#RUN chmod +x app.jar

EXPOSE 8080

## For some reason the app is exiting right after starting it if I use java with the fatjar
##ENTRYPOINT ["java", "-jar", "app.jar"]

## To avoid using exesive time on defining this Dockerfile used for test I will use:
CMD ["sbt", "run"]
## I do not like this solution and I know that there better ways to solve it but for the interview exercise is fine