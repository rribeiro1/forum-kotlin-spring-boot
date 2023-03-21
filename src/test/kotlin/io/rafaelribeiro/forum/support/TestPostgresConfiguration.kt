package io.rafaelribeiro.forum.support

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration
class TestPostgresConfiguration : ApplicationContextInitializer<ConfigurableApplicationContext> {

    companion object {
        private val POSTGRES_IMAGE = DockerImageName.parse("postgres:13-alpine")
        private val postgres = PostgreSQLContainer<Nothing>(POSTGRES_IMAGE)
            .apply { start() }
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        TestPropertyValues.of(
            "spring.datasource.url=${postgres.jdbcUrl}",
            "spring.datasource.username=${postgres.username}",
            "spring.datasource.password=${postgres.password}"
        ).applyTo(applicationContext)
    }
}
