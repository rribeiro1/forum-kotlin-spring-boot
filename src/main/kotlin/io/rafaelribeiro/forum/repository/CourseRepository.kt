package io.rafaelribeiro.forum.repository

import io.rafaelribeiro.forum.model.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface CourseRepository : JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    fun findByName(name: String): Course?
}
