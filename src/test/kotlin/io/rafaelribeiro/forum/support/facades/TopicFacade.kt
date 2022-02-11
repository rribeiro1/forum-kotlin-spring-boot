package io.rafaelribeiro.forum.support.facades

import io.rafaelribeiro.forum.model.Course
import io.rafaelribeiro.forum.model.Topic
import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.repository.TopicRepository
import io.rafaelribeiro.forum.support.ResourceNotFoundException
import org.springframework.stereotype.Component

@Component
class TopicFacade(private val topicRepository: TopicRepository) {

    fun findById(id: Long): Topic {
        return topicRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(id.toString(), Topic::class)
        }
    }

    fun create(title: String, message: String, user: User, course: Course): Topic {
        return topicRepository.save(Topic(title, message, course, user))
    }

    fun resetDatabase() = topicRepository.deleteAll()
}
