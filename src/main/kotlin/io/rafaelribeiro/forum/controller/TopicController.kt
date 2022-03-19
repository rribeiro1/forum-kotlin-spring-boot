package io.rafaelribeiro.forum.controller

import io.rafaelribeiro.forum.dtos.topic.TopicCreateDto
import io.rafaelribeiro.forum.dtos.topic.TopicDto
import io.rafaelribeiro.forum.dtos.topic.TopicUpdateDto
import io.rafaelribeiro.forum.security.ForumUserDetails
import io.rafaelribeiro.forum.service.TopicService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/topics")
class TopicController(
    private val topicService: TopicService
) {
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_TOPIC')")
    fun getById(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: ForumUserDetails
    ) = TopicDto.of(topicService.getById(id))

    @GetMapping
    @Cacheable("topic-list")
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_TOPIC')")
    fun list(
        @RequestParam(required = false) courseName: String? = null,
        @PageableDefault(sort = ["creationDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ) = topicService.getAll(courseName, pageable).map(TopicDto::of)

    @PostMapping
    @CacheEvict("topic-list", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_TOPIC')")
    fun create(
        @Valid @RequestBody input: TopicCreateDto,
        @AuthenticationPrincipal userDetails: ForumUserDetails
    ) = TopicDto.of(topicService.create(input.title, input.message, input.courseName, userDetails.id!!))

    @PutMapping("/{id}")
    @CacheEvict("topic-list", allEntries = true)
    @PreAuthorize("hasAuthority('UPDATE_TOPIC')")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody input: TopicUpdateDto
    ) = TopicDto.of(topicService.update(id, input.title, input.message))

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_TOPIC')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = topicService.delete(id)
}
