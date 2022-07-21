## Forum Application

[![CI/CD Pipeline](https://github.com/rribeiro1/forum-kotlin-spring-boot/actions/workflows/pipeline.yml/badge.svg)](https://github.com/rribeiro1/forum-kotlin-spring-boot/actions/workflows/pipeline.yml)
[![Test Coverage](https://api.codeclimate.com/v1/badges/6cdcb074e10579edd89f/test_coverage)](https://codeclimate.com/github/rribeiro1/forum-kotlin-spring-boot/test_coverage)
[![Maintainability](https://api.codeclimate.com/v1/badges/6cdcb074e10579edd89f/maintainability)](https://codeclimate.com/github/rribeiro1/forum-kotlin-spring-boot/maintainability)

### Built using

- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Security](https://spring.io/projects/spring-security)
- [Postgres](https://www.postgresql.org/)
- [Github Actions](https://docs.github.com/en/actions)
- [Code Climate](https://codeclimate.com/)
- [Instrumentation with Datadog](https://www.datadoghq.com/auto-instrumentation/)

### Getting started

Copy the `example.env` to `.env`:

```sh
cp example.env .env
```

### Spin up database with Docker composer

```sh
docker compose up
```

### Build

```sh
./gradlew build -x test
```

### Run

```sh
./gradlew bootRun

# With datadog agent
java -javaagent:libs/dd-java-agent.jar -Ddd.logs.injection=true -Ddd.service=forum -Ddd.env=local -jar build/libs/forum.jar
```

### Test

```sh
./gradlew test
```

### Endpoints

> You can run the [SQL script on the resources](src/main/resources/data.sql) path to include some records on the database.

### Authenticate

`POST` http://localhost:8080/auth
```json
{
    "email": "aluno@email.com",
    "password": "123456"
}
```

### Get user profile
`GET` http://localhost:8080/me
> Header: Authorization: Bearer `token`
