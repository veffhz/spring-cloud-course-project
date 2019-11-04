# Course management service (mvc, data, openfeign)

#### requirements:
java 8-11, gradle, mySql, liquibase, eureka (discovery service).

#### build
`./gradlew clean build`

#### run
`./gradlew bootRun` or `java -jar build/libs/course-management-service.jar`

#### documentation

Domain model:
* course
* transaction

Rest apis:
* /service/course/{courseId} GET
* /service/enroll POST
* /service/all GET
* /service/user/{userId} GET
* /service/instances GET
* /service/port GET
