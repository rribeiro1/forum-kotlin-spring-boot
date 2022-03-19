package io.rafaelribeiro.forum.security

import io.rafaelribeiro.forum.repository.TopicRepository
import org.springframework.stereotype.Service

@Service
class AuthorizationService(
    private val topicRepository: TopicRepository
) {
    fun topicBelongsToUser(userId: Long, topicId: Long): Boolean {
        return try {
            val topic = topicRepository.findById(topicId).get()
            topic.author.id == userId
        } catch (e: Exception) {
            false
        }
    }
}
