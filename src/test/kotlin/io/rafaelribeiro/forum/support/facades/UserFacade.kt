package io.rafaelribeiro.forum.support.facades

import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import io.rafaelribeiro.forum.config.PasswordEncoderConfig
import io.rafaelribeiro.forum.model.Privilege
import io.rafaelribeiro.forum.model.Role
import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.repository.PrivilegeRepository
import io.rafaelribeiro.forum.repository.RoleRepository
import io.rafaelribeiro.forum.repository.UserRepository
import io.rafaelribeiro.forum.security.TokenService
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val privilegeRepository: PrivilegeRepository,
    private val passwordEncoderConfig: PasswordEncoderConfig
) {
    @SpykBean
    private lateinit var tokenServiceMock: TokenService

    fun createAuthenticatedUser(name: String, email: String, password: String): User {
        val readTopic = Privilege("READ_TOPIC")
        val createTopic = Privilege("CREATE_TOPIC")
        val editTopic = Privilege("UPDATE_TOPIC")
        val deleteTopic = Privilege("DELETE_TOPIC")
        privilegeRepository.save(readTopic)
        privilegeRepository.save(createTopic)
        privilegeRepository.save(editTopic)
        privilegeRepository.save(deleteTopic)

        val adminRole = Role("ADMIN", mutableSetOf(readTopic, createTopic, editTopic, deleteTopic))
        roleRepository.save(adminRole)

        val user = User(name, email, passwordEncoderConfig.encoder().encode(password)).apply {
            this.roles.add(adminRole)
        }

        userRepository.save(user)
        mockAuthentication(user.id!!)
        return user
    }

    private fun mockAuthentication(userId: Long) {
        every { tokenServiceMock.isTokenValid(any()) } returns true
        every { tokenServiceMock.getUserId(any()) } returns userId
    }

    fun resetDatabase() {
        userRepository.deleteAll()
        privilegeRepository.deleteAll()
        roleRepository.deleteAll()
    }
}
