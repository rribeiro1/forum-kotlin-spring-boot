package br.com.alura.forum.controller

import br.com.alura.forum.dtos.topic.CreateTopicDto
import br.com.alura.forum.dtos.topic.TopicDto
import br.com.alura.forum.dtos.topic.TopicDtos
import br.com.alura.forum.repository.CourseRepository
import br.com.alura.forum.repository.TopicRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/topics")
class TopicController(
        private val topicRepository: TopicRepository,
        private val topicDtos: TopicDtos
) {
    @GetMapping
    fun list(courseName: String? = null) = when(courseName) {
            null -> topicRepository.findAll()
            else -> topicRepository.findByCourseName(courseName)
        }.map { topic -> topicDtos.convert(topic) }
}
