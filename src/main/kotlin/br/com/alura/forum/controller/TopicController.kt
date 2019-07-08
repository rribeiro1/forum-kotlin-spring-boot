package br.com.alura.forum.controller

import br.com.alura.forum.dtos.topic.TopicCreateDto
import br.com.alura.forum.dtos.topic.TopicDto
import br.com.alura.forum.dtos.topic.TopicDtos
import br.com.alura.forum.repository.CourseRepository
import br.com.alura.forum.repository.TopicRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/topics")
class TopicController(
        private val topicRepository: TopicRepository,
        private val courseRepository: CourseRepository,
        private val topicDtos: TopicDtos
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = topicRepository.findById(id)
            .map { topic -> topicDtos.convertToDetailDto(topic)}

    @GetMapping
    fun list(courseName: String? = null) = when(courseName) {
            null -> topicRepository.findAll()
            else -> topicRepository.findByCourseName(courseName)
        }.map { topic -> topicDtos.convertToDto(topic) }

    @PostMapping
    fun create(@RequestBody @Valid topicCreateDto: TopicCreateDto,
               uriBuilder: ServletUriComponentsBuilder): ResponseEntity<TopicDto> {
        val course = courseRepository.findByName(topicCreateDto.courseName)
        val topic = topicDtos.convertToEntity(topicCreateDto, course)
        val saved = topicRepository.save(topic)
        val uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.id).toUri()
        return ResponseEntity.created(uri).body(topicDtos.convertToDto(saved))
    }

}
