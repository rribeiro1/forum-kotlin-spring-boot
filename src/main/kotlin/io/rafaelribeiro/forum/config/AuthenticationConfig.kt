package io.rafaelribeiro.forum.config

import io.rafaelribeiro.forum.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AuthenticationConfig(
    private val userRepository: UserRepository
) {
    @Bean
    fun authenticationProvider() = DaoAuthenticationProvider().apply {
        setPasswordEncoder(encoder())
        setUserDetailsService(userDetailService())
    }

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailService() = UserDetailsService { username ->
        userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("Failed to authenticate user $username")
    }
}
