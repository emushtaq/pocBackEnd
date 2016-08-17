The Back-End for the POC

This is JAVA based backend made with the JAVA PLAY! framework (2.4).

1. Make you have Java and Activator installed. (Refer Play docs)
2. Run `activator "~run 8080"` . This is will resolve all dependencies and launch the webservice at port 8080.
3. Open up a browser and enter localhost:8080 to see if the webservice health is OK.


NOTE: If using the docker image , please run the following command:
`docker run --name play-8080 -p 8080:9000 [RESPOSITORY-NAME]:[TAG-NAME]`