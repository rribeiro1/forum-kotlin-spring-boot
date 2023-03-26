package io.rafaelribeiro.forum.config

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated

@Configuration
class JwtConfig(properties: Properties) {

    val secret = properties.secret
    val expiration = properties.expiration

    @Validated
    @ConfigurationProperties(prefix = "forum.jwt")
    data class Properties(
        @field:NotBlank val secret: String,
        @field:NotBlank val expiration: String
    )
}
