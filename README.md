# unit-conversion-exercise

Requirements:
- Docker is installed

## Running the service

To test this service you should follow these steps:

1. Building the docker image: `docker build --tag interview-project .`
2. Run the docker image in a container: `docker run -p8080:8080 --name conversion-service interview-project:latest`
3. Stop/Remove the running the container : `docker rm --force conversion-service`

## Running the tests

Requirements:
- sbt is installed

1. Run: `sbt compile test`

## Design decisions

I chose Akka Http because it was only one endpoint but due to that I ended up writing a Dockerfile that I dot not like 
(Read more in the Dockerfile)

In a bigger project I would choose finatra which is easier to use, avoids this issue with the Dockerfile at all, is fast
 and is a complete framework.

