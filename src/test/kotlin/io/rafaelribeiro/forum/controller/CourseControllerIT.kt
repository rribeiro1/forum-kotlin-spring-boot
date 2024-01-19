package io.rafaelribeiro.forum.controller

import io.rafaelribeiro.forum.model.Course
import io.rafaelribeiro.forum.model.Topic
import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.support.*
import io.restassured.filter.log.LogDetail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class CourseControllerIT : AbstractIT() {
    private lateinit var course: Course
    private lateinit var user: User
    private lateinit var topic: Topic

    private val courseName = "PHP Advanced"
    private val courseCategory = "Fantasy"
    private val topicTitle = "New Release"
    private val topicMessage = "Does not work"

    @BeforeEach
    fun setup() {
        val role = roleFacade.createRoleWithPrivileges(
            "ADMIN",
            listOf("READ_TOPIC", "CREATE_TOPIC", "DELETE_TOPIC", "UPDATE_TOPIC")
        )
        user = userFacade.createAuthenticatedUser(
            name = "Student",
            email = "aluno@email.com",
            password = "123456",
            role = role
        )
        course = courseFacade.create(courseName, courseCategory)
        topic = topicFacade.create(title = topicTitle, message = topicMessage, user, course)
    }

    @Test
    fun `should get courses by ids`() {
        authenticated()
            .get("/courses/ids")
            .then()
            .log().ifValidationFails(LogDetail.ALL)
            .statusCode(HttpStatus.OK)
    }
}
