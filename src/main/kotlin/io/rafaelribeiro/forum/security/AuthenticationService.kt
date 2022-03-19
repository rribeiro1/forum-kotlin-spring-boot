package io.rafaelribeiro.forum.security

import io.rafaelribeiro.forum.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null)
            throw UsernameNotFoundException("username not provided.")

        val user = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("Failed to authenticate user $username")

        return ForumUserDetails(user)
    }
}
