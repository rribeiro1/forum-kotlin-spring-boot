package io.rafaelribeiro.forum.controller

import io.rafaelribeiro.forum.dtos.topic.TopicCreateDto
import io.rafaelribeiro.forum.dtos.topic.TopicDto
import io.rafaelribeiro.forum.dtos.topic.TopicUpdateDto
import io.rafaelribeiro.forum.model.Course
import io.rafaelribeiro.forum.model.Topic
import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.support.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

class TopicControllerIT : AbstractIT() {
    private lateinit var course: Course
    private lateinit var user: User
    private lateinit var topic: Topic

    private val courseName = "PHP Advanced"
    private val courseCategory = "Fantasy"
    private val topicTitle = "New Release"
    private val topicMessage = "Does not work"

    @BeforeEach
    fun setup() {
        user = userFacade.createAuthenticatedUser(name = "Student", email = "aluno@email.com", password = "123456")
        course = courseFacade.create(courseName, courseCategory)
        topic = topicFacade.create(title = topicTitle, message = topicMessage, user, course)
    }

    @Nested
    inner class WhenGettingUsers {
        @Test
        fun `should get list of topics`() {
            val newTopic = topicFacade.create(title = "New Topic", message = "Any Message", user, course)

            val actual = authenticated()
                .get("/topics")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK)
                .extractContent(TopicDto::class)

            assertThat(actual).extracting("message").contains(topicMessage, newTopic.message)
            assertThat(actual).extracting("title").contains(topicTitle, newTopic.title)
        }

        @Test
        fun `should get list of topics by course name`() {
            val kotlinCourse = courseFacade.create("Kotlin", courseCategory)
            val newTopic = topicFacade.create(title = "New Topic 1", message = "Any Message 1", user, kotlinCourse)

            val actual = authenticated()
                .queryParam("courseName", kotlinCourse.name)
                .get("/topics")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK)
                .extractContent(TopicDto::class)

            assertThat(actual).extracting("message").contains(newTopic.message)
            assertThat(actual).extracting("title").contains(newTopic.title)
        }
    }

    @Test
    fun `should get topic by id`() {
        val actual = authenticated()
            .get("/topics/{id}", topic.id)
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK)
            .extract(TopicDto::class)

        assertThat(actual.id).isNotNull
        assertThat(actual.title).isEqualTo(topicTitle)
        assertThat(actual.message).isEqualTo(topicMessage)
    }

    @Test
    fun `should create a new topic`() {
        val input = TopicCreateDto(title = "New Release", message = "Help me!", courseName = course.name)

        val actual = authenticated()
            .body(input)
            .post("/topics")
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.CREATED)
            .extract(TopicDto::class)

        assertThat(actual.id).isNotNull
        assertThat(actual.title).isEqualTo(input.title)
        assertThat(actual.message).isEqualTo(input.message)
    }

    @Test
    fun `should update a topic`() {
        val input = TopicUpdateDto(title = "New title")

        val actual = authenticated()
            .body(input)
            .put("/topics/{id}", topic.id)
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK)
            .extract(TopicDto::class)

        assertThat(actual.title).isEqualTo(input.title)
        assertThat(actual.message).isEqualTo(topic.message)
    }

    @Test
    fun `should delete a topic`() {
        authenticated()
            .delete("/topics/{id}", topic.id)
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.NO_CONTENT)

        assertThrows<ResourceNotFoundException> {
            topicFacade.findById(topic.id!!)
        }
    }
}
