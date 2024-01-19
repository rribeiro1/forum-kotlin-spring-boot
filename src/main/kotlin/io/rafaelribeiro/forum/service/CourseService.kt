package io.rafaelribeiro.forum.service

import io.rafaelribeiro.forum.dtos.course.CourseFilterDto
import io.rafaelribeiro.forum.model.Course
import io.rafaelribeiro.forum.repository.CourseRepository
import io.rafaelribeiro.forum.support.ResourceNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CourseService(
    private val courseRepository: CourseRepository
) {
    @Transactional(readOnly = true)
    fun findCourse(name: String) = courseRepository.findByName(name)
        ?: throw ResourceNotFoundException(name, Course::class)

    fun search(filter: CourseFilterDto, pageable: Pageable): Page<Course> {
        return courseRepository.findAll(filter.toSpecification(), pageable)
    }

    fun idsIn(courseIds: List<UUID>): List<Course> {
        return courseRepository.findAll(CourseFilterDto.idIn(courseIds))
    }
}
