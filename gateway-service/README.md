# Zuul proxy service (Zuul API Gateway)

#### requirements:
java 8-11, gradle, eureka (discovery service).

#### build
`./gradlew clean build`

#### run
`./gradlew bootRun` or `java -jar build/libs/gateway-service.jar`

#### documentation

Api to:
* user-service http://localhost:8765/api/user/service/
* course-service http://localhost:8765/api/course/service
