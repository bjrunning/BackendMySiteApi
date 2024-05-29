# BackendMySiteApi

[![Actions Status](https://github.com/bjrunning/BackendMySiteApi/actions/workflows/main.yaml/badge.svg)](https://github.com/bjrunning/BackendMySiteApi/actions/workflows/main.yaml)
[![Maintainability](https://api.codeclimate.com/v1/badges/5fc98d2cc5c44fb2ad42/maintainability)](https://codeclimate.com/github/bjrunning/BackendMySiteApi/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/5fc98d2cc5c44fb2ad42/test_coverage)](https://codeclimate.com/github/bjrunning/BackendMySiteApi/test_coverage)

### RESTFul API: CRUD implementation. User creation, update and deletion. Creating posts, updating them and deleting them. Authentication with JWT. Documentation OpenAPI, Swagger.

## Stack
REST API, Security, JWT, JPA, Hibernate, Docker, JUnit, Mockito, Instancio, Faker, Mapstruct, OpenAPI, Swagger, PostgreSQL, Sentry, Checkstyle, Lombok, Jacoco, Postman

## Demo

`http post localhost:8080/api/login`
![1.png](images/1.png)

`http localhost:8080/api/users`
![2.png](images/2.png)

`http post localhost:8080/api/users firstName=Ivan lastName=Ivanov email=ivanov@example password=ivanov`
![3.png](images/3.png)

`http put localhost:8080/api/users/2 firstName=EeOneGay lastName=EeOneGayev`
![4.png](images/4.png)

`http localhost:8080/api/posts`
![5.png](images/5.png)

`http post localhost:8080/api/posts authorId=1 slug=example name=example body=example`
![6.png](images/6.png)

`http put localhost:8080/api/posts/10 authorId=1 slug=example-updated name=example-updated body=example-updated`
![7.png](images/7.png)