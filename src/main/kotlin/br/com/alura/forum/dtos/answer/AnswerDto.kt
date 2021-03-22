package br.com.alura.forum.dtos.answer

import br.com.alura.forum.model.Answer
import java.time.LocalDateTime

data class AnswerDto(
    val id: Long,
    val message: String,
    val creationDate: LocalDateTime?,
    val authorName: String
) {
    companion object Factory {
        fun of(answer: Answer): AnswerDto {
            return AnswerDto(
                id = answer.id!!,
                message = answer.message!!,
                creationDate = answer.creationDate,
                authorName = answer.author!!.name
            )
        }
    }
}
