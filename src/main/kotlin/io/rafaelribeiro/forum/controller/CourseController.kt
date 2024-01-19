package io.rafaelribeiro.forum.controller

import io.rafaelribeiro.forum.dtos.course.CourseFilterDto
import io.rafaelribeiro.forum.service.CourseService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.LocalTime.MAX
import java.time.LocalTime.MIN
import java.util.*

@RestController
@RequestMapping("/courses")
class CourseController(
    private val courseService: CourseService
) {

    @GetMapping
    fun search(
        @Parameter(hidden = true) @PageableDefault(sort = ["creationDate"], direction = Sort.Direction.ASC)
        pageable: Pageable,
        name: String?,
        category: String?,
        from: String?,
        to: String?
    ) = courseService.search(CourseFilterDto(name, category, from.toLocalDateTime(), to.toLocalDateTime(MAX)), pageable)

    @GetMapping("/ids")
    fun idsIn() = courseService.idsIn(listOf(UUID.randomUUID()))

    private fun String?.toLocalDateTime(time: LocalTime = MIN) =
        this?.let { LocalDateTime.of(LocalDate.parse(it), time) }
}
