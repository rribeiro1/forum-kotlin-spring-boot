package br.com.alura.forum.controller

import br.com.alura.forum.dtos.auth.LoginDto
import br.com.alura.forum.dtos.auth.TokenDto
import br.com.alura.forum.support.AbstractIT
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AuthenticationControllerIT : AbstractIT() {

    @Nested
    inner class WithValidLogin {
        @Test
        fun `should create a new token`() {
            userFacade.createUser(email = "aluno@email.com", password = "123456")

            val actual = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(LoginDto(email = "aluno@email.com", password = "123456"))
                .post("auth")
                .then()
                .statusCode(200)
                .extract()
                .body().`as`(TokenDto::class.java)

            assertThat(actual.type).isEqualTo("Bearer")
            assertThat(actual.token).isNotNull
        }
    }

    @Nested
    inner class WithInvalidLogin {
        @Test
        fun `should return bad request`() {
            val loginDto = LoginDto(email = "invalid@email.com", password = "invalid")

            RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(loginDto)
                .post("auth")
                .then()
                .statusCode(403)
        }
    }
}
