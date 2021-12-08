    -------------------------------------------------
# Run / check application
   
- `http://localhost:8000`
- `http://localhost:8000/service/instances`

### JAR - Prerequisite:
- Create Gradle Wrapper for application: `gradle wrapper`
  https://docs.gradle.org/current/userguide/gradle_wrapper.html
### JAR - Create and Run
- Create **JAR**
    - Local installations:
        - `gradle clean build` or if maven:
        - `mvn clean install`
    - With wrapper (no local installation needed)
        - `./gradlew clean build`

- Run
    - `java –jar <JARFILE> ` (inside */build/libs* )

-------------------------------------------------
# Liquibase
Creates database and table-changes automatically when application is run,
by using changelog files.


# Repository

https://www.baeldung.com/spring-data-jpa-query

- `extends JpaRepository<User, Long>`
- `@Query("select u.name from User u where u.id in (:pIdList)")`  ...use JPQL in repository


-------------------------------------------------
# HTTP REST Controller

- `@RequestBody`
- `@RequestParam`
- `ResponseEntity<?>` wrapper containing/controlling return entity and response method



-------------------------------------------------
# Security

- Frontend sends data with **authentication encrypted heading**
    - **btoa** https://developer.mozilla.org/en-US/docs/Glossary/Base64
    - Checking in backend by `UserDetailService`
- Backend **password encryption**
    - implemented by **Spring Boot Security**
    - Authorize requests for different websites (`ant.Matchers(...)`)
    - Authentication implemented with `ProviderManager`
- **Authentication** ... who are you (username, password)
    - HTTP Filters (used by spring security)
    - **Authentication** (is) **Object**, containing of
        - Principal
        - Credentials
        - Authorities
        -

## Spring Security

- Default configurations about
    - userclass
    - userservice
    - login path
    - ...
- **UserDetailService**
    - `public class UserDetailServiceImpl implements UserDetailsService`


-------------------------------------------------
# Eureka
Microservices can communicate which each other via **Eureka** by using application-names. 