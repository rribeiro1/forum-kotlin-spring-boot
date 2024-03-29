package io.rafaelribeiro.forum.security

import io.opentracing.util.GlobalTracer
import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.support.ContextHelper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class ExtraTracingFilter : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(ExtraTracingFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authentication = SecurityContextHolder.getContext()?.authentication

            if (authentication?.isAuthenticated == true && authentication.principal is User) {
                val span = GlobalTracer.get().activeSpan()

                if (span != null) {
                    val principal = authentication.principal as User

                    val userId = principal.id.toString()
                    val userEmail = principal.email
                    val headerRequestId = request.getHeader("X-REQUEST-ID")
                    val requestId = headerRequestId ?: UUID.randomUUID().toString()

                    span.setTag(ContextHelper.USER_ID, userId)
                    span.setTag(ContextHelper.USER_EMAIL, userEmail)
                    span.setTag(ContextHelper.REQUEST_ID, requestId)

                    ContextHelper.setContext(userId, userEmail, requestId)
                }
            }
        } catch (e: Exception) {
            log.error("Error occurred while setting request context", e)
        } finally {
            filterChain.doFilter(request, response)
        }
    }
}
