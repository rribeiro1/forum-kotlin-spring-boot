package br.com.alura.forum.controller

import br.com.alura.forum.dtos.Topic.TopicDto
import br.com.alura.forum.dtos.Topic.TopicsDto
import br.com.alura.forum.repository.TopicRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ForumController(
        private val topicRepository: TopicRepository,
        private val topicsDtos: TopicsDto
) {
    @GetMapping("/topics")
    fun list(): List<TopicDto> {
        return topicRepository.findAll().map { topic -> topicsDtos.convert(topic) }
    }
}