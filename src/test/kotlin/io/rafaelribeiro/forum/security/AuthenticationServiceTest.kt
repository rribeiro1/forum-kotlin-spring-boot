package io.rafaelribeiro.forum.security

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.rafaelribeiro.forum.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.core.userdetails.UsernameNotFoundException

@ExtendWith(MockKExtension::class)
class AuthenticationServiceTest(
    @MockK private val userRepositoryMock: UserRepository
) {
    private val underTest = AuthenticationService(userRepositoryMock)

    @Test
    fun `should return UsernameNotFoundException when username is not provided`() {
        assertThrows<UsernameNotFoundException> {
            underTest.loadUserByUsername(null)
        }
    }
}
