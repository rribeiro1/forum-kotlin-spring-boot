package io.rafaelribeiro.forum.repository

import io.rafaelribeiro.forum.model.Topic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TopicRepository : JpaRepository<Topic, UUID> {
    fun findByCourseName(courseName: String, pageable: Pageable): Page<Topic>
}
