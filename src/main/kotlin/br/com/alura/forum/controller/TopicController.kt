package br.com.alura.forum.controller

import br.com.alura.forum.dtos.topic.CreateTopicDto
import br.com.alura.forum.dtos.topic.TopicDto
import br.com.alura.forum.dtos.topic.TopicDtos
import br.com.alura.forum.model.Topic
import br.com.alura.forum.repository.CourseRepository
import br.com.alura.forum.repository.TopicRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/topics")
class TopicController(
        private val topicRepository: TopicRepository,
        private val courseRepository: CourseRepository,
        private val topicDtos: TopicDtos
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = topicRepository.findById(id).map { topic -> topicDtos.convertToDto(topic)}

    @GetMapping
    fun list(courseName: String? = null) = when(courseName) {
            null -> topicRepository.findAll()
            else -> topicRepository.findByCourseName(courseName)
        }.map { topic -> topicDtos.convertToDto(topic) }

    @PostMapping
    fun create(@RequestBody createTopicDto: CreateTopicDto, uriBuilder: ServletUriComponentsBuilder): ResponseEntity<TopicDto> {
        val topic = topicRepository.save(topicDtos.convertToEntity(createTopicDto, courseRepository.findByName(createTopicDto.courseName)))
        val uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.id).toUri()
        return ResponseEntity.created(uri).body(topicDtos.convertToDto(topic))
    }

}
