# user-service
Rest Service implementation for user details management. 
API supports Get And Put endpoints to retrieve and update user details respectively.

# Implementation Details –

* Rest service is implemented using Spring Batch framework.
* Implemented using Java 13.
* SLF4J is the logging framework.
* In-memory H2 database
* Used Lombok utility library.
* Application supports security feature build using Spring Security framework. 
* Application is configured to be fault tolerant i.e. if there are any issue in DB connectivity, Hystrix fallback methods will be invoked to return default results.
* Application uses Aspects for method start and end logging.
* Spring PACT is used for Contract testing.

## Software

* Java 13
* H2 Database
* Git
* Maven 3.6+
* IDE 

    IDE to be used is developers choice, but Intellij is highly recommended.
    
## Plugins

* lombok

## Libraries used

* Spring Boot 2.2.6.RELEASE
* Spring Security
* Spring Aspects
* Lombok
* Hystrix

## General Project Guidelines
* Application performs database setup on application startup. This includes schema definition along with test data insertion in respective tables.
* Application requires security header to be passed for API operations. For test purpose, Postman can be used with Basic Auth header having username - "1001" and password -"test".
* **Application allows only "Admin" user to perform update operation. For test purpose, a user with userId 1001 is configured to be "Admin" user. So, only this user can perform update operation.
if other user like "1003" is passed in the header which is not an "Admin" user, then API operation will fail with 401 Http error code.**
* Application generates user-service.log file. Application is pre-configured to generate this file at "C:/Users/assignment/user-service.log" location.
However, log file directory location is configurable via "logging.file.name" property inside application.properties.

## Building and deploying the application

### Building the application
The project uses Maven as a build tool. It already contains
pom.xml file which includes all the dependencies required for the project.

### Running the application
* Build project after checkout using "mvn clean install"
* Using Intellij, run Application.java class as spring boot application.
* Once Application is started rest endpoints can be triggered using Postman to validate the API response. 


## Samples -
* Get User 
    * Endpoint - http://localhost:8080/users/1001
    * Header - Basic Auth with username - 1001, password- test
    * Method - GET
    * Response - 
        `{
            "title": "Mr",
            "firstName": "fname1",
            "lastName": "lname1",
            "gender": "male",
            "address": {
                "street": "1 street",
                "city": "sydney",
                "state": "nsw",
                "postcode": "2000"
            }
        }`
 * Get ALL Users 
     * Endpoint - http://localhost:8080/users
     * Header - Basic Auth with username - 1001, password- test
     * Method - GET
     * Response - 
         `[
              {
                  "title": "Mr",
                  "firstName": "fname1",
                  "lastName": "lname1",
                  "gender": "male",
                  "address": {
                      "street": "1 street",
                      "city": "sydney",
                      "state": "nsw",
                      "postcode": "2001"
                  }
              },
              {
                  "title": "Mr",
                  "firstName": "fname2",
                  "lastName": "lname2",
                  "gender": "male",
                  "address": {
                      "street": "2 street",
                      "city": "sydney",
                      "state": "nsw",
                      "postcode": "2000"
                  }
              },
              {
                  "title": "Mr",
                  "firstName": "fname3",
                  "lastName": "lname3",
                  "gender": "male",
                  "address": {
                      "street": "3 street",
                      "city": "sydney",
                      "state": "nsw",
                      "postcode": "2000"
                  }
              },
              {
                  "title": "Mr",
                  "firstName": "fname4",
                  "lastName": "lname4",
                  "gender": "male",
                  "address": {
                      "street": "4 street",
                      "city": "sydney",
                      "state": "nsw",
                      "postcode": "2000"
                  }
              },
              {
                  "title": "Mr",
                  "firstName": "fname5",
                  "lastName": "lname5",
                  "gender": "male",
                  "address": {
                      "street": "5 street",
                      "city": "sydney",
                      "state": "nsw",
                      "postcode": "2000"
                  }
              }
          ]`
* Update User 
    * Endpoint - http://localhost:8080/users/1001
    * Header - Basic Auth with username - 1001, password- test
    * Method - PUT
    * Request Body -  
        `{
            "title": "Mr",
            "firstName": "fname1",
            "lastName": "lname1",
            "gender": "male",
            "address": {
                "street": "1 street",
                "city": "sydney",
                "state": "nsw",
                "postcode": "2001"
            }
        }`
    * Response - 
        `{
            "title": "Mr",
            "firstName": "fname1",
            "lastName": "lname1",
            "gender": "male",
            "address": {
                "street": "1 street",
                "city": "sydney",
                "state": "nsw",
                "postcode": "2000"
            }
        }`
## Notes
   Due to limited time scope, assigment is done with basic implementation of each of the required application framework libraries.
   However, following changes can be enhanced in the next release of the change  -

   * API validations
   * More UTs and ITs can be written to cover more boundary conditions.
   * Validation on input records can be extended to handle other failure scenarios.
   * Profiles can be added to handle different environment behaviours.
   * PACT tests can have more test cases. 

## Postman Script Link
https://www.getpostman.com/collections/5ca475d69388932e378b
