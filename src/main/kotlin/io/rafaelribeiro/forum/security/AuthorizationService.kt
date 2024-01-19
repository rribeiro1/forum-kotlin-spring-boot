package io.rafaelribeiro.forum.security

import io.rafaelribeiro.forum.repository.TopicRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthorizationService(
    private val topicRepository: TopicRepository
) {
    fun topicBelongsToUser(userId: UUID, topicId: UUID): Boolean {
        return try {
            val topic = topicRepository.findById(topicId).get()
            topic.author.id == userId
        } catch (e: Exception) {
            false
        }
    }
}
