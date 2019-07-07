package br.com.alura.forum.controller

import br.com.alura.forum.dtos.topic.TopicDto
import br.com.alura.forum.dtos.topic.TopicsDto
import br.com.alura.forum.repository.TopicRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ForumController(
        private val topicRepository: TopicRepository,
        private val topicsDtos: TopicsDto
) {
    @GetMapping("/topics")
    fun list(@RequestParam courseName: String? = null): List<TopicDto> {
        if (courseName == null)
            return topicRepository.findAll().map { topic -> topicsDtos.convert(topic) }
        else
            return topicRepository.findByCourseName(courseName).map { topic -> topicsDtos.convert(topic) }
    }
}