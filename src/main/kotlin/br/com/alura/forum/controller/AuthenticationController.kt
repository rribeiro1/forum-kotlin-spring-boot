package br.com.alura.forum.controller

import br.com.alura.forum.config.security.TokenService
import br.com.alura.forum.dtos.auth.LoginDto
import br.com.alura.forum.dtos.auth.TokenDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    @PostMapping
    fun auth(@Valid @RequestBody loginDto: LoginDto): TokenDto {
        val credentials = UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
        val authentication = authenticationManager.authenticate(credentials)
        val token = tokenService.createToken(authentication)
        return TokenDto.from("Bearer", token)
    }
}
