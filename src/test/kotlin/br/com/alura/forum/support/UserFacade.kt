package br.com.alura.forum.support

import br.com.alura.forum.config.security.PasswordEncoderConfig
import br.com.alura.forum.model.User
import br.com.alura.forum.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository,
    private val passwordEncoderConfig: PasswordEncoderConfig
) {
    fun createUser(email: String, password: String): User {
        val user = User()
        user.email = email
        user.pass = passwordEncoderConfig.encoder().encode(password)
        return userRepository.save(user)
    }

    fun resetDatabase() {
        userRepository.deleteAll()
    }
}