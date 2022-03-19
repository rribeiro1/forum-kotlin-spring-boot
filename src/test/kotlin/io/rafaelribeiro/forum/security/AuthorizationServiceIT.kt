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
    private lateinit var anotherUser: User

    @BeforeEach
    fun setup() {
        course = courseFacade.create("Kotlin", "Programming")
        user = userFacade.createAuthenticatedUser("Rafael", "rafael@gmail.com", "12345")
        anotherUser = userFacade.createAuthenticatedUser("Another", "another@email.com", "12345")
        topic = topicFacade.create("New topic", "New Message", user, course)
    }

    @Test
    fun `should test whether a topic belongs to the user`() {
        assertThat(underTest.topicBelongsToUser(user.id!!, topic.id!!)).isTrue
        assertThat(underTest.topicBelongsToUser(anotherUser.id!!, topic.id!!)).isFalse
    }
}
