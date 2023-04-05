package io.rafaelribeiro.forum.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.rafaelribeiro.forum.config.JwtConfig
import io.rafaelribeiro.forum.model.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey
import kotlin.reflect.KFunction

@Service
class JwtService(
    private val jwtConfig: JwtConfig
) {
    fun createToken(authentication: Authentication, extraClaims: Map<String, Any> = mapOf()): String {
        val creationDate = Date()
        return Jwts.builder()
            .setIssuer("Forum API")
            .setClaims(extraClaims)
            .setSubject((authentication.principal as User).email)
            .setIssuedAt(creationDate)
            .setExpiration((Date(creationDate.time + jwtConfig.expiration.toLong())))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    fun isTokenExpired(token: String) = extractClaim(token, Claims::getExpiration).before(Date())

    fun extractUsername(jwt: String): String? = extractClaim(jwt, Claims::getSubject)

    private fun <T> extractClaim(token: String, claimResolver: KFunction<T>): T {
        return extractAllClaims(token).let { claimResolver.call(it) }
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(jwtConfig.secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
