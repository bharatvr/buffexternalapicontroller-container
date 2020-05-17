#### Project : Buff reporting back-end API service

#### Getting Started: -
 * Make sure you have JDK 1.8 and maven install in your machine
 * Checkout repository in your local work space.
 * Got to repository and check mvn command is working  ( **mvn --version**)
 * Run maven build:
 	Command :  **mvn clean install**
 	Output must be some thing like below :
 	
 		[INFO] ------------------------------------------------------------------------
		[INFO] BUILD SUCCESS
		[INFO] ------------------------------------------------------------------------
		[INFO] Total time:  9.267 s
		[INFO] Finished at: 2019-09-19T20:45:40-07:00
		[INFO] ------------------------------------------------------------------------
 	
 * Update environment variable which required in applicaion.yml
 
 *. Start application with command in terminal: ** mvn spring-boot:run
 	
 	Output must be something like below on console :
 	
 			Tomcat started on port(s): 8080 (http) with context path '/buff'


 	
* You can check service health at : http://localhost:8080/buff/common/api/healthCheck
 
 Output Must like : 
 
		{"responseBody":"Service is up and running","responseStatus":{"status":"Success","errors":null,"appendMap":null}}

