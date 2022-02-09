package br.com.alura.forum.support.facades

import br.com.alura.forum.model.Course
import br.com.alura.forum.model.Topic
import br.com.alura.forum.model.User
import br.com.alura.forum.repository.TopicRepository
import br.com.alura.forum.support.ResourceNotFoundException
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
