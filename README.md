# Repository
https://www.baeldung.com/spring-data-jpa-query

- `extends JpaRepository<User, Long>`
- `@Query("select u.name from User u where u.id in (:pIdList)")`  ...use JPQL in repository