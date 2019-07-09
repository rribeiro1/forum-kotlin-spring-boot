package br.com.alura.forum.dtos.answer

import br.com.alura.forum.model.Answer
import org.springframework.stereotype.Component

@Component
class AnswerDtos {
    fun convert(answer: Answer) = AnswerDto(
            answer.id!!,
            answer.message!!,
            answer.creationDate,
            answer.author!!.name!!
    )
}