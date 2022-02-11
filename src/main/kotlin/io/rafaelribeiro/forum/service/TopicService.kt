package io.rafaelribeiro.forum.service

import io.rafaelribeiro.forum.model.Topic
import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.repository.TopicRepository
import io.rafaelribeiro.forum.support.ResourceNotFoundException
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TopicService(
    private val topicRepository: TopicRepository,
    private val courseService: CourseService
) {
    @Transactional(readOnly = true)
    fun getById(id: Long): Topic {
        return topicRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(id.toString(), Topic::class)
        }
    }

    @Transactional
    fun create(title: String, message: String, courseName: String, user: User): Topic {
        val course = courseService.findCourse(courseName)
        val topic = Topic(title, message, course, user)
        return topicRepository.save(topic)
    }

    @Transactional
    fun update(id: Long, title: String?, message: String?): Topic {
        val topic = getById(id)
        title?.let { topic.title = it }
        message?.let { topic.message = it }
        return topicRepository.save(topic)
    }

    @Transactional
    @PreAuthorize("@authorizationService.topicBelongsToUser(#user, #topic)")
    fun delete(topic: Topic) = topicRepository.delete(topic)
}
