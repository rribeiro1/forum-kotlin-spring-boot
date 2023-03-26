package io.rafaelribeiro.forum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan("io.rafaelribeiro.forum.config")
class ForumApplicationConfig

@EnableCaching
@SpringBootApplication
class ForumApplication

fun main(args: Array<String>) {
    runApplication<ForumApplication>(*args)
}
