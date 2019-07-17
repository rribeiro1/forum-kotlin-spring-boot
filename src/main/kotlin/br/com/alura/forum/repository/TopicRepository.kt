package br.com.alura.forum.repository

import br.com.alura.forum.model.Topic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface TopicRepository : PagingAndSortingRepository<Topic, Long> {
    fun findByCourseName(courseName: String, pageable: Pageable): Page<Topic>
}