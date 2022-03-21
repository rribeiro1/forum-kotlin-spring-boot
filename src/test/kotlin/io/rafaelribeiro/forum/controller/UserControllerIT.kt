package io.rafaelribeiro.forum.controller

import io.rafaelribeiro.forum.dtos.user.PrivilegeDto
import io.rafaelribeiro.forum.dtos.user.UserDto
import io.rafaelribeiro.forum.model.Role
import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.support.AbstractIT
import io.rafaelribeiro.forum.support.extract
import io.rafaelribeiro.forum.support.statusCode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class UserControllerIT : AbstractIT() {
    private lateinit var user: User
    private lateinit var role: Role

    @BeforeEach
    fun setup() {
        role = roleFacade.createRoleWithPrivileges("ADMIN", listOf("READ", "WRITE", "DELETE"))
        user = userFacade.createAuthenticatedUser(name = "Student", email = "aluno@email.com", password = "123456", role = role)
    }

    @Test
    fun `should get info about the logged user`() {
        val actual = authenticated()
            .get("/me")
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK)
            .extract(UserDto::class)

        assertThat(actual.name).isEqualTo(user.name)
        assertThat(actual.email).isEqualTo(user.email)
        assertThat(actual.roles).extracting("name").containsExactly(role.name)
        with(actual.roles.first()) {
            assertThat(privileges).containsExactlyInAnyOrder(
                PrivilegeDto("WRITE"),
                PrivilegeDto("READ"),
                PrivilegeDto("DELETE")
            )
        }
    }
}
