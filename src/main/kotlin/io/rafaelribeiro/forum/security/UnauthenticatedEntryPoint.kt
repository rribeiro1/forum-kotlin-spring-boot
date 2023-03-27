package io.rafaelribeiro.forum.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.rafaelribeiro.forum.dtos.auth.AuthenticationErrorDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class UnauthenticatedEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        val res = ServletServerHttpResponse(response)

        res.setStatusCode(HttpStatus.UNAUTHORIZED)
        res.servletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

        val payload = objectMapper.writeValueAsString(
            AuthenticationErrorDto(
                status = HttpStatus.UNAUTHORIZED,
                message = "Authentication error",
                errors = listOf(authException.message.toString())
            )
        )

        res.body.write(payload.toByteArray())
    }
}
