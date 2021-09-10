<h3 align="center">Transactions REST API</h3>

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [Built With](#built-with)
* [Getting Started](#getting-started)
    * [Clone](#clone)
    * [Build ](#build-the-project)
    * [Run](#run-the-app)
    * [Check](#check-the-homepage)
*  [Usage](#usage)

## Built With

* Java 16
* Spring Boot 2.5.4
  * Spring-boot-starter-data-jpa
  * Spring-boot-starter-web 
  * Spring-boot-starter-actuator
  * Spring-boot-starter-validation 
  * Spring-boot-starter-security


* Springdoc Openapi 
* jjwt 
* Mapstruct
* Project Lombok
* H2 Database
  
<!-- GETTING STARTED -->
## Getting Started

**Requirements** : JDK 16.  
**Clone the repo and follow these simple steps:**

### Clone:
```
git clone git@github.com:huseyinkombayci/transactions-rest-api.git
```

### Build the project:
```
./gradlew clean build
```

### Run the app:
```
./gradlew bootRun
```

### Check the homepage:
```
localhost:8080
```

## Usage

* [Health Endpoint](http://localhost:8080/actuator/health)
* [Swagger Docs](http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config)
* [H2 Console](http://localhost:8080/h2-console/)
* [Postman Collection](http://localhost:8080/postman-collection.json)


## Test App User 
```
username: alan.turing@test.com
password: test-pass
```
