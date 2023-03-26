package io.rafaelribeiro.forum.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.rafaelribeiro.forum.repository.UserRepository
import io.rafaelribeiro.forum.security.AuthenticationService
import io.rafaelribeiro.forum.security.TokenAuthenticationFilter
import io.rafaelribeiro.forum.security.TokenService
import io.rafaelribeiro.forum.security.UnauthenticatedEntryPoint
import io.rafaelribeiro.forum.support.ExtraTracingFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
    private val authenticationService: AuthenticationService,
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val extraTracingFilter: ExtraTracingFilter,
    private val objectMapper: ObjectMapper
) {
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    // -- Handle authorizations
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.authorizeHttpRequests()
            .requestMatchers("/auth", "/actuator/**").permitAll()
            .anyRequest()
            .authenticated()
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(TokenAuthenticationFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter::class.java)
            .userDetailsService(authenticationService)
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
