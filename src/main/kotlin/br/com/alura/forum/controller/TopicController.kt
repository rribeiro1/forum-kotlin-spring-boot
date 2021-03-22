package br.com.alura.forum.controller

import br.com.alura.forum.dtos.topic.TopicCreateDto
import br.com.alura.forum.dtos.topic.TopicDto
import br.com.alura.forum.dtos.topic.TopicDtos
import br.com.alura.forum.dtos.topic.TopicUpdateDto
import br.com.alura.forum.model.Topic
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
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * It was implemented cache for studies purpose, in a real scenario it makes more sense implement it in tables where
 * data is not update with frequency e.g Street names, etc.
 */
@RestController
@RequestMapping("/topics")
class TopicController(
    private val topicService: TopicService,
    private val topicRepository: TopicRepository,
    private val courseRepository: CourseRepository,
    private val topicDtos: TopicDtos
) {
    @GetMapping("/{id}")
    fun getTopic(@PathVariable id: Long) = TopicDto.of(topicService.getTopic(id))

    @GetMapping
    @Cacheable("topic-list")
    @Transactional(readOnly = true)
    fun list(
        @RequestParam(required = false) courseName: String? = null,
        @PageableDefault(sort = ["creationDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ) = when (courseName) {
        null -> topicRepository.findAll(pageable)
        else -> topicRepository.findByCourseName(courseName, pageable)
    }.map(TopicDto.Factory::of)

    @PostMapping
    @Transactional
    @CacheEvict("topic-list", allEntries = true)
    fun create(
        @RequestBody @Validated topicCreateDto: TopicCreateDto,
        @AuthenticationPrincipal userDetails: User
    ): TopicDto {
        val course = courseRepository.findByName(topicCreateDto.courseName)
        val topic = Topic(
            topicCreateDto.title,
            topicCreateDto.message,
            course,
            userDetails
        )
        val saved = topicRepository.save(topic)
        return TopicDto.of(saved)
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict("topic-list", allEntries = true)
    fun update(@PathVariable id: Long, @RequestBody @Validated topicUpdateDto: TopicUpdateDto): TopicDto {
        val updated = topicDtos.apply(topicService.getTopic(id), topicUpdateDto)
        return TopicDto.of(updated)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @AuthenticationPrincipal user: User,
        @PathVariable id: Long
    ) = topicService.deleteTopic(user, topicService.getTopic(id))
}
