package io.rafaelribeiro.forum.dtos.auth

import org.springframework.http.HttpStatus
import java.time.OffsetDateTime

data class AuthenticationErrorDto(
    val status: HttpStatus,
    val message: String,
    val errors: List<String>? = null
) {
    val timestamp: OffsetDateTime = OffsetDateTime.now()
}
