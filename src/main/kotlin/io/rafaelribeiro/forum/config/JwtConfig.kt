package io.rafaelribeiro.forum.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "forum.jwt")
class JwtConfig {
    var expiration: String? = null
    var secret: String? = null
}
