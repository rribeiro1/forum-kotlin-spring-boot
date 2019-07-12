package br.com.alura.forum.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableWebSecurity
@Configuration
class SecurityConfiguration(
        private val authenticationService: AuthenticationService
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    //-- Handle authentications
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authenticationService).passwordEncoder(BCryptPasswordEncoder())
    }

    //-- Handle authorizations
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/topics").permitAll()
                .antMatchers(HttpMethod.GET, "/topics/*").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}
