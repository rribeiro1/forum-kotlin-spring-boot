package io.rafaelribeiro.forum.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.rafaelribeiro.forum.repository.TopicRepository
import io.rafaelribeiro.forum.support.ResourceNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class TopicServiceTest(
    @MockK private val topicRepositoryMock: TopicRepository,
    @MockK private val courseServiceMock: CourseService
) {
    private val underTest = TopicService(topicRepositoryMock, courseServiceMock)

    @Test
    fun `should throw ResourceNotFound exception when the topic is not found`() {
        val notFoundTopic = 1L
        every { topicRepositoryMock.findById(notFoundTopic) } returns Optional.empty()

        val exception = assertThrows<ResourceNotFoundException> {
            underTest.getById(notFoundTopic)
        }

        assertThat(exception.message).isEqualTo("Topic with $notFoundTopic is not found")
    }
}