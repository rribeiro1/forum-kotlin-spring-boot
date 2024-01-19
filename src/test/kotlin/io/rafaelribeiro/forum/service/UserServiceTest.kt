package io.rafaelribeiro.forum.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.rafaelribeiro.forum.repository.UserRepository
import io.rafaelribeiro.forum.support.ResourceNotFoundException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class UserServiceTest(
    @MockK private val userRepositoryMock: UserRepository
) {
    private val underTest = UserService(userRepositoryMock)

    @Test
    fun `should throw ResourceNotFound exception when the user is not found`() {
        val notFoundUser = UUID.randomUUID()
        every { userRepositoryMock.findById(notFoundUser) } returns Optional.empty()

        val exception = assertThrows<ResourceNotFoundException> {
            underTest.findById(notFoundUser)
        }

        Assertions.assertThat(exception.message).isEqualTo("User with $notFoundUser is not found")
    }
}
