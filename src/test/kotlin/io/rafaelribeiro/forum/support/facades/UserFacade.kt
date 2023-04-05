package io.rafaelribeiro.forum.support.facades

import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import io.rafaelribeiro.forum.model.Role
import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.repository.UserRepository
import io.rafaelribeiro.forum.security.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @SpykBean
    private lateinit var jwtService: JwtService

    fun createAuthenticatedUser(name: String, email: String, password: String, role: Role): User {
        val user = User(name, email, passwordEncoder.encode(password)).apply {
            this.roles.add(role)
        }

        userRepository.save(user)
        mockAuthentication(user.email)
        return user
    }

    private fun mockAuthentication(email: String) {
        every { jwtService.isTokenValid(any(), any()) } returns true
        every { jwtService.extractUsername(any()) } returns email
    }

    fun resetDatabase() {
        userRepository.deleteAll()
    }
}
