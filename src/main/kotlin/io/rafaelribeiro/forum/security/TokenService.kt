package io.rafaelribeiro.forum.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.rafaelribeiro.forum.config.JwtConfig
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    private val jwtConfig: JwtConfig
) {
    fun createToken(authentication: Authentication): String {
        val creationDate = Date()
        return Jwts.builder()
            .setIssuer("Forum API")
            .setSubject((authentication.principal as ForumUserDetails).id.toString())
            .setIssuedAt(creationDate)
            .setExpiration((Date(creationDate.time + jwtConfig.expiration.toLong())))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.secret)
            .compact()
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(jwtConfig.secret)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUserId(token: String) =
        Jwts.parserBuilder()
            .setSigningKey(jwtConfig.secret)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
            .toLong()
}
