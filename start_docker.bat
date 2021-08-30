docker-compose -f docker-compose.yml up -d
docker run -d --network="host" uiprojdockerimage mvn test -DsuiteXMLFile=/temp/salmon/UIFromScratch/SmokeSuite.xml