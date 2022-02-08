package br.com.alura.forum.config.security

import br.com.alura.forum.repository.UserRepository
import br.com.alura.forum.support.AuthenticationErrorException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthenticationService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null)
            throw AuthenticationErrorException("username not provided.")

        return userRepository.findByEmail(username).orElseThrow {
            throw AuthenticationErrorException("Failed to authenticate user $username")
        }
    }
}
