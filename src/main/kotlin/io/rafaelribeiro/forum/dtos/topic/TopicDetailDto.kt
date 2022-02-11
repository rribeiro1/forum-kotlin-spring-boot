package io.rafaelribeiro.forum.dtos.topic

import io.rafaelribeiro.forum.dtos.answer.AnswerDto
import io.rafaelribeiro.forum.model.TopicStatus
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
