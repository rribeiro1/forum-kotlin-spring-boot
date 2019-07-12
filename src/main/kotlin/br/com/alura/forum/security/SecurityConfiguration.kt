package br.com.alura.forum.security

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableWebSecurity
@Configuration
class SecurityConfiguration(
        private val authenticationService: AuthenticationService
) : WebSecurityConfigurerAdapter() {

    //-- Handle authentications
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authenticationService).passwordEncoder(BCryptPasswordEncoder())
    }

    //-- Handle authorizations
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/topics").permitAll()
                .antMatchers(HttpMethod.GET, "/topics/*").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
    }


}
