package io.rafaelribeiro.forum.support.facades

import io.rafaelribeiro.forum.model.Course
import io.rafaelribeiro.forum.repository.CourseRepository
import org.springframework.stereotype.Component

@Component
class CourseFacade(private val courseRepository: CourseRepository) {
    fun create(name: String, category: String): Course {
        return courseRepository.save(Course(name, category))
    }

    fun resetDatabase() = courseRepository.deleteAll()
}
