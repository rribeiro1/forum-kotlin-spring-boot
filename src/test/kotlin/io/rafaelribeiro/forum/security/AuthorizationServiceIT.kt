package io.rafaelribeiro.forum.security

import io.rafaelribeiro.forum.model.Course
import io.rafaelribeiro.forum.model.Topic
import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.support.AbstractIT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class AuthorizationServiceIT(
    @Autowired private val underTest: AuthorizationService
) : AbstractIT() {

    private lateinit var topic: Topic
    private lateinit var user: User
    private lateinit var course: Course

    @BeforeEach
    fun setup() {
        course = courseFacade.create("Kotlin", "Programming")
        user = userFacade.createAuthenticatedUser("Rafael", "rafael@gmail.com", "12345")
        topic = topicFacade.create("New topic", "New Message", user, course)
    }

    @Test
    fun `should return true when the topic belongs to the user`() {
        val actual = underTest.topicBelongsToUser(user, topic.id!!)
        assertThat(actual).isTrue
    }

    @Test
    fun `should return false when the topic does not belong to the user`() {
        val anotherUser = userFacade.createAuthenticatedUser("Another", "another@email.com", "12345")
        val actual = underTest.topicBelongsToUser(anotherUser, topic.id!!)
        assertThat(actual).isFalse
    }

    @Test
    fun `should return false when the topic does not exist`() {
        val actual = underTest.topicBelongsToUser(user, Long.MIN_VALUE)
        assertThat(actual).isFalse
    }
}