package io.rafaelribeiro.forum.repository

import io.rafaelribeiro.forum.model.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository : JpaRepository<Course, Long> {
    fun findByName(name: String): Course?
}
