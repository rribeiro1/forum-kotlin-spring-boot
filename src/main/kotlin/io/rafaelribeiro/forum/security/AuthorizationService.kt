package io.rafaelribeiro.forum.security

import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.service.TopicService
import io.rafaelribeiro.forum.support.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthorizationService(
    private val topicService: TopicService
) {
    fun topicBelongsToUser(user: User, topicId: Long): Boolean {
        return try {
            val topic = topicService.getById(topicId)
            topic.author.id == user.id
        } catch (e: ResourceNotFoundException) {
            false
        }
    }
}
