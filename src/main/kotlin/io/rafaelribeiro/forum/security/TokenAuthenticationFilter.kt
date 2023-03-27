package io.rafaelribeiro.forum.security

import io.rafaelribeiro.forum.repository.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TokenAuthenticationFilter(
    private val tokenService: TokenService,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    // Login endpoint does not execute this filter, token does not exist at this point.
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = retrieveToken(request)

        try {
            if (tokenService.isTokenValid(token)) {
                val userId = tokenService.getUserId(token)
                val user = userRepository.findById(userId).get()
                val userDetails = ForumUserDetails(user)
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } finally {
            filterChain.doFilter(request, response)
        }
    }

    private fun retrieveToken(request: HttpServletRequest): String {
        val token = request.getHeader("Authorization")
        return if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            ""
        } else token.substring(7, token.length)
    }
}
