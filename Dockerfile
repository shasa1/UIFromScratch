FROM maven:3.8.2-jdk-11-slim

WORKDIR /temp/salmon/UIFromScratch/

#COPY ./Docker /temp/salmon/UIFromScratch/Docker
COPY ./src /temp/salmon/UIFromScratch/src
#COPY ./LogFiles /temp/salmon/UIFromScratch/LogFiles

COPY ./pom.xml /temp/salmon/UIFromScratch/pom.xml
COPY ./SmokeSuite.xml /temp/salmon/UIFromScratch/SmokeSuite.xml
COPY ./docker-compose.yml /temp/salmon/UIFromScratch/docker-compose.yml
COPY ./JenkinsFile /temp/salmon/UIFromScratch/JenkinsFile

RUN mvn -f /temp/salmon/UIFromScratch/pom.xml clean compile

