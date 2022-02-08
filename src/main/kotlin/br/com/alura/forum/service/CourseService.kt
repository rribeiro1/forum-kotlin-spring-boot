package br.com.alura.forum.service

import br.com.alura.forum.model.Course
import br.com.alura.forum.repository.CourseRepository
import br.com.alura.forum.support.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService(
    private val courseRepository: CourseRepository
) {
    @Transactional(readOnly = true)
    fun findCourse(name: String) = courseRepository.findByName(name)
        ?: throw ResourceNotFoundException(name, Course::class)
}