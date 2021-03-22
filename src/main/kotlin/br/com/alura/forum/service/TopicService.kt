package br.com.alura.forum.service

import br.com.alura.forum.model.Topic
import br.com.alura.forum.model.User
import br.com.alura.forum.repository.TopicRepository
import br.com.alura.forum.support.ResourceNotFoundException
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TopicService(
    private val topicRepository: TopicRepository
) {
    @Transactional(readOnly = true)
    @Throws(ResourceNotFoundException::class)
    fun getTopic(id: Long): Topic {
        return topicRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(id, Topic::class)
        }
    }

    @Transactional
    @PreAuthorize("@authorizationService.topicBelongsToUser(#user, #topic)")
    fun deleteTopic(user: User, topic: Topic) {
        topicRepository.delete(topic)
    }
}
