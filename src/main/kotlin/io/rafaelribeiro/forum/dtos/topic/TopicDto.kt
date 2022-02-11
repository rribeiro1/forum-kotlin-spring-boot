package io.rafaelribeiro.forum.dtos.topic

import io.rafaelribeiro.forum.dtos.answer.AnswerDto
import io.rafaelribeiro.forum.model.Topic
import java.time.LocalDateTime

data class TopicDto(
    val id: Long,
    val title: String,
    val message: String,
    val creationDate: LocalDateTime
) {
    companion object Factory {
        fun of(topic: Topic): TopicDto {
            return TopicDto(
                topic.id!!,
                topic.title,
                topic.message,
                topic.creationDate
            )
        }

        fun details(topic: Topic): TopicDetailDto {
            return TopicDetailDto(
                topic.id!!,
                topic.title,
                topic.message,
                topic.creationDate,
                topic.author.name,
                topic.status,
                topic.answers.map(AnswerDto::of)
            )
        }
    }
}
