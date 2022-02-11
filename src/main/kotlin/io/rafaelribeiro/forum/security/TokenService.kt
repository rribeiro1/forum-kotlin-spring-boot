package io.rafaelribeiro.forum.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.rafaelribeiro.forum.config.JwtConfig
import io.rafaelribeiro.forum.model.User
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.Date

@Service
class TokenService(
    private val jwtConfig: JwtConfig
) {
    fun createToken(authentication: Authentication): String {
        val creationDate = Date()
        return Jwts.builder()
            .setIssuer("Forum API")
            .setSubject((authentication.principal as User).id.toString())
            .setIssuedAt(creationDate)
            .setExpiration((Date(creationDate.time + jwtConfig.expiration!!.toLong())))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.secret)
            .compact()
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(jwtConfig.secret).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUserId(token: String) = Jwts
        .parser()
        .setSigningKey(jwtConfig.secret)
        .parseClaimsJws(token)
        .body
        .subject
        .toLong()
}
