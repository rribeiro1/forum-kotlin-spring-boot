package io.rafaelribeiro.forum.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.rafaelribeiro.forum.dtos.auth.AuthenticationErrorDto
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Suppress("SpringJavaInjectionPointsAutowiringInspection")
class UnauthenticatedEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        val res = ServletServerHttpResponse(response)

        // TODO: Backwards compatibility, change to HttpStatus.UNAUTHORIZED when it's safe for clients.
        val httpStatusCode = HttpStatus.FORBIDDEN

        res.setStatusCode(httpStatusCode)
        res.servletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

        val payload = objectMapper.writeValueAsString(
            AuthenticationErrorDto(
                status = httpStatusCode,
                message = "Authentication error",
                errors = listOf(authException.message.toString())
            )
        )

        res.body.write(payload.toByteArray())
    }
}
