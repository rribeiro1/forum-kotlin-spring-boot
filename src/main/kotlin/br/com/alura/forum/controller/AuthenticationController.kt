package br.com.alura.forum.controller

import br.com.alura.forum.config.security.TokenService
import br.com.alura.forum.dtos.auth.LoginDto
import br.com.alura.forum.dtos.auth.TokenDto
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import javax.naming.AuthenticationException
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    @PostMapping
    fun auth(@RequestBody @Valid loginDto: LoginDto): ResponseEntity<TokenDto> {
        val credentials = UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
        return try {
            val authentication = authenticationManager.authenticate(credentials)
            val token = tokenService.createToken(authentication)
            ResponseEntity.ok(TokenDto(token, "Bearer"))
        } catch (e: AuthenticationException) {
            ResponseEntity.badRequest().build()
        }
    }
}