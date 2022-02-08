package br.com.alura.forum.controller

import br.com.alura.forum.dtos.topic.TopicCreateDto
import br.com.alura.forum.dtos.topic.TopicDto
import br.com.alura.forum.dtos.topic.TopicUpdateDto
import br.com.alura.forum.model.User
import br.com.alura.forum.repository.CourseRepository
import br.com.alura.forum.repository.TopicRepository
import br.com.alura.forum.service.TopicService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * Cache was implemented here as studies purpose, in a real scenario it makes more sense to implement it in tables where
 * data is not updated frequently e.g. Street names, etc.
 */
@RestController
@RequestMapping("/topics")
class TopicController(
    private val topicService: TopicService,
    private val topicRepository: TopicRepository,
    private val courseRepository: CourseRepository
) {
    @GetMapping("/{id}")
    fun getTopic(@PathVariable id: Long) = TopicDto.of(topicService.getById(id))

    @GetMapping
    @Cacheable("topic-list")
    @Transactional(readOnly = true)
    fun list(
        @RequestParam(required = false) courseName: String? = null,
        @PageableDefault(sort = ["creationDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ) = when (courseName) {
        null -> topicRepository.findAll(pageable)
        else -> topicRepository.findByCourseName(courseName, pageable)
    }.map(TopicDto::of)

    @PostMapping
    @Transactional
    @CacheEvict("topic-list", allEntries = true)
    fun create(
        @Valid @RequestBody input: TopicCreateDto,
        @AuthenticationPrincipal userDetails: User
    ) = TopicDto.of(topicService.create(input.title, input.message, input.courseName, userDetails))

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict("topic-list", allEntries = true)
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody input: TopicUpdateDto
    ) = TopicDto.of(topicService.update(id, input.title, input.message))

    @DeleteMapping("/{id}")
    fun delete(
        @AuthenticationPrincipal user: User,
        @PathVariable id: Long
    ) = topicService.delete(topicService.getById(id))
}
