package io.rafaelribeiro.forum.controller

import io.rafaelribeiro.forum.dtos.user.PrivilegeDto
import io.rafaelribeiro.forum.dtos.user.UserDto
import io.rafaelribeiro.forum.model.Role
import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.support.AbstractIT
import io.rafaelribeiro.forum.support.GraphQLQuery
import io.rafaelribeiro.forum.support.extract
import io.rafaelribeiro.forum.support.statusCode
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
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

    // TODO: Fix authorization for GraphQL endpoint.
    @Test
    @Disabled
    fun `should get info about logged user via graphQl`() {
        val query = """
            {
                user {
                    id,
                    name,
                    email
                }
            }
        """.trimIndent()

        authenticated()
            .body(GraphQLQuery(query))
            .post("/graphql")
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK)
            .and()
            .body("data.user.id", notNullValue())
            .body("data.user.name", equalTo("Student"))
            .body("data.user.email", equalTo("aluno@email.com"))
    }
}
