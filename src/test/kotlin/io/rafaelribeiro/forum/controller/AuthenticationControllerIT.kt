package io.rafaelribeiro.forum.controller

import io.rafaelribeiro.forum.dtos.auth.LoginDto
import io.rafaelribeiro.forum.dtos.auth.TokenDto
import io.rafaelribeiro.forum.support.AbstractIT
import io.rafaelribeiro.forum.support.extract
import io.rafaelribeiro.forum.support.statusCode
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class AuthenticationControllerIT : AbstractIT() {

    @Nested
    inner class WithValidLogin {
        @Test
        fun `should create a new token`() {
            userFacade.createAuthenticatedUser(name = "Student", email = "aluno@email.com", password = "123456")

            val actual = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(LoginDto(email = "aluno@email.com", password = "123456"))
                .post("auth")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.OK)
                .extract(TokenDto::class)

            assertThat(actual.type).isEqualTo("Bearer")
            assertThat(actual.token).isNotNull
        }
    }

    @Nested
    inner class WithInvalidLogin {
        @Test
        fun `should return forbidden request`() {
            val loginDto = LoginDto(email = "invalid@email.com", password = "invalid")

            RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(loginDto)
                .post("/auth")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.FORBIDDEN)
        }
    }
}
