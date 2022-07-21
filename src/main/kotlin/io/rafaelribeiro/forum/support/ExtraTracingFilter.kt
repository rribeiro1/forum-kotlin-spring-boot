package io.rafaelribeiro.forum.support

import io.opentracing.util.GlobalTracer
import io.rafaelribeiro.forum.security.ForumUserDetails
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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

            if (authentication?.isAuthenticated == true && authentication.principal is ForumUserDetails) {
                val span = GlobalTracer.get().activeSpan()

                if (span != null) {
                    val principal = authentication.principal as ForumUserDetails

                    val userId = principal.id.toString()
                    val userEmail = principal.user.email
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
