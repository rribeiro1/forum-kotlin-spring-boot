package br.com.alura.forum.config.security

import br.com.alura.forum.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationFilter(
        private val tokenService: TokenService,
        private val userRepository: UserRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = retrieveToken(request)
        if (tokenService.isTokenValid(token)) {
            val userId = tokenService.getUserId(token)
            val user = userRepository.findById(userId).get()
            val authentication = UsernamePasswordAuthenticationToken(user, null, user.profiles)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    fun retrieveToken(request: HttpServletRequest): String {
        val token = request.getHeader("Authorization")
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return ""
        }
        else return token.substring(7, token.length)
    }
}