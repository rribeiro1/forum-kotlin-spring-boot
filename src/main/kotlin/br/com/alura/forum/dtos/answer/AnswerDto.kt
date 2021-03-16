package br.com.alura.forum.dtos.answer

import java.time.LocalDateTime

data class AnswerDto(
    val id: Long,
    val message: String,
    val creationDate: LocalDateTime?,
    val authorName: String
)