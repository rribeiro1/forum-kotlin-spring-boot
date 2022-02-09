package br.com.alura.forum.controller

import br.com.alura.forum.dtos.topic.TopicCreateDto
import br.com.alura.forum.dtos.topic.TopicDto
import br.com.alura.forum.dtos.topic.TopicUpdateDto
import br.com.alura.forum.model.Course
import br.com.alura.forum.model.Topic
import br.com.alura.forum.model.User
import br.com.alura.forum.support.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
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

    @Test
    fun `should get topic by id`() {
        val actual = authenticated()
            .get("/topics/{id}", topic.id)
            .then()
            .log().ifError()
            .statusCode(HttpStatus.OK)
            .extract(TopicDto::class)

        assertThat(actual.id).isNotNull
        assertThat(actual.title).isEqualTo(topicTitle)
        assertThat(actual.message).isEqualTo(topicMessage)
    }

    @Test
    fun `should get list of topics`() {
        val newTopic = topicFacade.create(title = "New Topic", message = "Any Message", user, course)

        val actual = authenticated()
            .get("/topics")
            .then()
            .log().ifError()
            .statusCode(HttpStatus.OK)
            .extractContent(TopicDto::class)

        assertThat(actual).extracting("message").contains(topicMessage, newTopic.message)
        assertThat(actual).extracting("title").contains(topicTitle, newTopic.title)
    }

    @Test
    fun `should create a new topic`() {
        val input = TopicCreateDto(title = "New Release", message = "Help me!", courseName = course.name)

        val actual = authenticated()
            .body(input)
            .post("/topics")
            .then()
            .log().ifError()
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
            .log().ifError()
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
            .log().ifError()
            .statusCode(HttpStatus.NO_CONTENT)

        assertThrows<ResourceNotFoundException> {
            topicFacade.findById(topic.id!!)
        }
    }
}
