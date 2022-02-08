package br.com.alura.forum.config.security

import br.com.alura.forum.model.User
import br.com.alura.forum.service.TopicService
import br.com.alura.forum.support.ResourceNotFoundException
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
