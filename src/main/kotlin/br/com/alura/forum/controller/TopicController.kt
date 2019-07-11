package br.com.alura.forum.controller

import br.com.alura.forum.dtos.topic.TopicCreateDto
import br.com.alura.forum.dtos.topic.TopicDto
import br.com.alura.forum.dtos.topic.TopicDtos
import br.com.alura.forum.dtos.topic.TopicUpdateDto
import br.com.alura.forum.repository.CourseRepository
import br.com.alura.forum.repository.TopicRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
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
    fun findAllById(@PathVariable id: Long) = topicRepository.findById(id)
            .map { topic -> topicDtos.convertToDetailDto(topic)}

    @GetMapping
    @Transactional(readOnly = true)
    fun list(@RequestParam(required = false) courseName: String? = null,
             @PageableDefault(sort = ["creationDate"], direction = Sort.Direction.DESC) pageable: Pageable) =
            when(courseName) {
                null -> topicRepository.findAll(pageable)
                else -> topicRepository.findByCourseName(courseName, pageable)
        }.map { topic -> topicDtos.convertToDto(topic) }

    @PostMapping
    @Transactional
    fun create(@RequestBody @Valid topicCreateDto: TopicCreateDto,
               uriBuilder: ServletUriComponentsBuilder): ResponseEntity<TopicDto> {
        val course = courseRepository.findByName(topicCreateDto.courseName)
        val topic = topicDtos.convertToEntity(topicCreateDto, course)
        val saved = topicRepository.save(topic)
        val uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.id).toUri()
        return ResponseEntity.created(uri).body(topicDtos.convertToDto(saved))
    }

    @PutMapping("/{id}")
    @Transactional
    fun update(@PathVariable id: Long, @RequestBody @Valid topicUpdateDto: TopicUpdateDto): ResponseEntity<TopicDto> {
        val updated = topicDtos.apply(findById(id), topicUpdateDto)
        return ResponseEntity.ok(topicDtos.convertToDto(updated))
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        topicRepository.delete(findById(id))
        return ResponseEntity.ok().build()
    }

    fun findById(id: Long) = topicRepository.findById(id)
            .orElseThrow { throw NoSuchElementException("resource not found") }
}
