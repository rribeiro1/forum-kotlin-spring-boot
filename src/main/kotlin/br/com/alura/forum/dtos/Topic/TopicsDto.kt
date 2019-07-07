package br.com.alura.forum.dtos.Topic

import java.time.LocalDateTime

data class TopicDto(
        val id: Long,
        val title: String,
        val message: String,
        val dateCreation: LocalDateTime
)