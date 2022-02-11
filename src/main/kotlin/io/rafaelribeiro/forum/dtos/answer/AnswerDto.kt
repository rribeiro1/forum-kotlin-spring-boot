package io.rafaelribeiro.forum.dtos.answer

import io.rafaelribeiro.forum.model.Answer
import io.rafaelribeiro.forum.support.DtoFactory
import java.time.LocalDateTime

data class AnswerDto(
    val id: Long,
    val message: String,
    val creationDate: LocalDateTime?,
    val authorName: String
) {
    companion object : DtoFactory<Answer, AnswerDto> {
        override fun of(model: Answer) = AnswerDto(
            id = model.id!!,
            message = model.message,
            creationDate = model.creationDate,
            authorName = model.author.name
        )
    }
}
