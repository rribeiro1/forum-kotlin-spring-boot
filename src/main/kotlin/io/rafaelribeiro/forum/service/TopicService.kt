package io.rafaelribeiro.forum.service

import io.rafaelribeiro.forum.model.Topic
import io.rafaelribeiro.forum.repository.TopicRepository
import io.rafaelribeiro.forum.support.ResourceNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TopicService(
    private val topicRepository: TopicRepository,
    private val courseService: CourseService,
    private val userService: UserService
) {
    @Transactional(readOnly = true)
    fun getAll(courseName: String? = null, pageable: Pageable) = when (courseName) {
        null -> topicRepository.findAll(pageable)
        else -> topicRepository.findByCourseName(courseName, pageable)
    }

    @Transactional(readOnly = true)
    @PreAuthorize("@authorizationService.topicBelongsToUser(principal.id, #id)")
    fun getById(id: Long): Topic {
        return topicRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(id.toString(), Topic::class)
        }
    }

    @Transactional
    fun create(title: String, message: String, courseName: String, userId: Long): Topic {
        val user = userService.findById(userId)
        val course = courseService.findCourse(courseName)
        val topic = Topic(title, message, course, user)
        return topicRepository.save(topic)
    }

    @Transactional
    @PreAuthorize("@authorizationService.topicBelongsToUser(principal.id, #id)")
    fun update(id: Long, title: String?, message: String?): Topic {
        val topic = getById(id)

        val editedTopic = topic.copy(
            title = title ?: topic.title,
            message = message ?: topic.message
        )

        return topicRepository.save(editedTopic)
    }

    @Transactional
    @PreAuthorize("@authorizationService.topicBelongsToUser(principal.id, #topicId)")
    fun delete(topicId: Long) = topicRepository.deleteById(topicId)
}
