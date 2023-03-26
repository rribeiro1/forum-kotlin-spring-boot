## Forum Application

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/221662af97db4831a2ccaba55a3f8ce7)](https://app.codacy.com/gh/rribeiro1/forum-kotlin-spring-boot/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/221662af97db4831a2ccaba55a3f8ce7)](https://app.codacy.com/gh/rribeiro1/forum-kotlin-spring-boot/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage)

### Built using

- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Security](https://spring.io/projects/spring-security)
- [Postgres](https://www.postgresql.org/)
- [Github Actions](https://docs.github.com/en/actions)
- [Code Climate](https://codeclimate.com/) and [Codacy](https://codacy.com)
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
