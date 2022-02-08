package br.com.alura.forum.dtos.answer

import br.com.alura.forum.model.Answer
import br.com.alura.forum.support.DtoFactory
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
