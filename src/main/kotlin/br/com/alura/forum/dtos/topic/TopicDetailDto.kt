package br.com.alura.forum.dtos.topic

import br.com.alura.forum.dtos.answer.AnswerDto
import br.com.alura.forum.model.TopicStatus
import java.time.LocalDateTime

data class TopicDetailDto(
    val id: Long,
    val title: String,
    val message: String,
    val dateCreation: LocalDateTime,
    val authorName: String,
    val status: TopicStatus,
    val answers: List<AnswerDto>
)
