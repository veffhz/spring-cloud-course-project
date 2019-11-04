# User management service (mvc, data, security)

#### requirements:
java 8-11, gradle, mySql, liquibase, eureka (discovery service).

#### build
`./gradlew clean build`

#### run
`./gradlew bootRun` or `java -jar build/libs/user-management-service.jar`

### documentation

Domain model:
* user

Rest apis:
* /service/login GET (Base auth header)
* /service/test GET
* /service/registration POST
* /service/names GET
* /service/instances GET
* /service/services GET
* /service/port GET
