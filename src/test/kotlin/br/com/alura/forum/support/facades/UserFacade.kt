package br.com.alura.forum.support.facades

import br.com.alura.forum.config.security.PasswordEncoderConfig
import br.com.alura.forum.config.security.TokenService
import br.com.alura.forum.model.User
import br.com.alura.forum.repository.UserRepository
import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
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
