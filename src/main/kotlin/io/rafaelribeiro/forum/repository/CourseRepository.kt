package io.rafaelribeiro.forum.repository

import io.rafaelribeiro.forum.model.Course
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository : JpaRepository<Course, Long> {
    fun findByName(name: String): Course?

    fun findAll(spec: Specification<Course>?, pageable: Pageable): Page<Course>
}
