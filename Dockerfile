# Use the official openjdk image from Docker Hub
FROM openjdk:17-jdk-alpine

ENV artifactId=NewsAggregator

#Setting up author and description label for metadata of image
LABEL author="Manish Kumar"
LABEL description="Postgres image for newsapp db"

#Setting the work directly location in docker container
WORKDIR /usr/backendapp

#Copying the generated jar file NewsAggregator-0.0.1-SNAPSHOT.jar into docker image as app.jar
COPY ./target/$artifactId.jar app.jar

# Expose the default port
EXPOSE 8080

#Command to start the spring boot application on docker run
ENTRYPOINT ["java", "-jar", "/usr/backendapp/app.jar", "--nytApiKey=$NYT_API_KEY", "--guardianApiKey=$GUARDIAN_API_KEY"]