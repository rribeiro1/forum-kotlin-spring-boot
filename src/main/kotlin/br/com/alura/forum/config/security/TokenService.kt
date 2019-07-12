package br.com.alura.forum.config.security

import br.com.alura.forum.config.JwtConfig
import br.com.alura.forum.model.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
        private val jwtConfig: JwtConfig
) {

    fun createToken(authentication: Authentication) =
            Jwts.builder()
                .setIssuer("Forum API")
                .setSubject((authentication.principal as User).id.toString())
                .setIssuedAt(Date())
                .setExpiration((Date(jwtConfig.expiration!!.toLong())))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.secret!!)
                .compact()
}
