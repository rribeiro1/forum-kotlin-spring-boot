package io.rafaelribeiro.forum.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.rafaelribeiro.forum.security.ExtraTracingFilter
import io.rafaelribeiro.forum.security.TokenAuthenticationFilter
import io.rafaelribeiro.forum.security.UnauthenticatedEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val tokenAuthenticationFilter: TokenAuthenticationFilter,
    private val extraTracingFilter: ExtraTracingFilter,
    private val objectMapper: ObjectMapper
) {
    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }

    @Bean
    fun authenticationProvider(
        userDetailsService: UserDetailsService,
        passwordEncoder: PasswordEncoder
    ): AuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder)
        daoAuthenticationProvider.setUserDetailsService(userDetailsService)
        return daoAuthenticationProvider
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/auth").permitAll()
            .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterAfter(extraTracingFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling().authenticationEntryPoint(UnauthenticatedEntryPoint(objectMapper))
            .and()
            .build()
    }

    @Bean
    fun ignoreResources(http: HttpSecurity): SecurityFilterChain {
        return http.securityMatcher(
            "/graphiql/**",
            "/**.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
        )
            .authorizeHttpRequests().anyRequest().permitAll()
            .and().requestCache().disable()
            .securityContext().disable()
            .sessionManagement().disable()
            .build()
    }
}
