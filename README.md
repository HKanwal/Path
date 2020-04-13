
Run Instructions: To run the front and back end simultaneously, need two different console instances open.

For Front-end:
- Stay in root directory (Path)
- Type into console "npm i" to install all the missing dependencies
- Type into console "npm start"
- Then you should be redirected to your browser where a localhost front end should be shown. This is the main page. (Now refer to the backend start).
- To change the hazard rating, type in a valid car type and valid destination coordinates, then click the button

- The graph shown is a visualization of the percentage crashes based on the car type. This calculation was done in the backend and can be found in the API. The other data that was used to calculate the Hazard is also mentioned in the backend, but isn't shown in the front end as we felt just the vehicle type was fine.

- The Hazard Rating shows 0 at all times due to issues we encountered when connecting the front and backend. We were unable to get the POST request sent from the front end to successfully be read by the backend, so it was never able to display this result. Though, again, the calculation for the ranking is in the backend and is shown in the API.

For Back-end:
- Stay in the root directory
- Type into console "mvn clean install"
- Then type "java -jar target/spring-boot-management-example-0.0.1-SNAPSHOT.jar"
- This should load the server, and it will be actively running in the background.
