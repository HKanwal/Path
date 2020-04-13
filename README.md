
Run Instructions: To run the front and back end simultaneously, need two different console instances open.

For Front-end:
- Stay in root directory (Path)
- Type into console "npm i" to install all the missing dependencies
- Type into console "npm start"
- Then you should be redirected to your browser where a localhost front end should be shown. This is the main page. (Now refer to the backend start).
- To change the hazard rating, type in a valid car type and valid destination coordinates, then click the button

For Back-end:
- Stay in the root directory
- Type into console "mvn clean install"
- Then type "java -jar target/spring-boot-management-example-0.0.1-SNAPSHOT.jar"
- This should load the server, and it will be actively running in the background.
