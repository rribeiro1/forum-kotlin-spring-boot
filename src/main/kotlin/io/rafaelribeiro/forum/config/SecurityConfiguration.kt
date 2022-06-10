package io.rafaelribeiro.forum.config

import io.rafaelribeiro.forum.repository.UserRepository
import io.rafaelribeiro.forum.security.AuthenticationService
import io.rafaelribeiro.forum.security.TokenAuthenticationFilter
import io.rafaelribeiro.forum.security.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
    private val authenticationService: AuthenticationService,
    private val tokenService: TokenService,
    private val userRepository: UserRepository
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    // -- Handle authentications
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authenticationService).passwordEncoder(BCryptPasswordEncoder())
    }

    // -- Handle authorizations
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/graphql").permitAll()
            .antMatchers(HttpMethod.GET, "/topics").permitAll()
            .antMatchers(HttpMethod.GET, "/topics/*").permitAll()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(TokenAuthenticationFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter::class.java)
    }

    // -- static resources(js, css, images, etc)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/graphiql/**",
            "/**.html",
            "/webjars/**",
            "/configuration/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
        )
    }
}
