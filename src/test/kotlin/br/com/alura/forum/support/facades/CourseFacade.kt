package br.com.alura.forum.support.facades

import br.com.alura.forum.model.Course
import br.com.alura.forum.repository.CourseRepository
import org.springframework.stereotype.Component

@Component
class CourseFacade(private val courseRepository: CourseRepository) {
    fun create(name: String, category: String): Course {
        return courseRepository.save(Course(name, category))
    }

    fun resetDatabase() = courseRepository.deleteAll()
}
