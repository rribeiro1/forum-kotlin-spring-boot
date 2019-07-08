package br.com.alura.forum.repository

import br.com.alura.forum.model.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository : JpaRepository<Course, Long> {

    fun findByName(name: String): Course
}