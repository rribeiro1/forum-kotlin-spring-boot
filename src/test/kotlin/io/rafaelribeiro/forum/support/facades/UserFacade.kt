package io.rafaelribeiro.forum.support.facades

import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import io.rafaelribeiro.forum.config.PasswordEncoderConfig
import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.repository.UserRepository
import io.rafaelribeiro.forum.security.TokenService
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository,
    private val passwordEncoderConfig: PasswordEncoderConfig
) {
    @SpykBean
    private lateinit var tokenServiceMock: TokenService

    fun createAuthenticatedUser(name: String, email: String, password: String): User {
        val user = User(name, email, passwordEncoderConfig.encoder().encode(password))
        userRepository.save(user)
        mockAuthentication(user.id!!)
        return user
    }

    private fun mockAuthentication(userId: Long) {
        every { tokenServiceMock.isTokenValid(any()) } returns true
        every { tokenServiceMock.getUserId(any()) } returns userId
    }

    fun resetDatabase() = userRepository.deleteAll()
}
