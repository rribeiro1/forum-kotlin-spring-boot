package br.com.alura.forum.dtos.topic

import br.com.alura.forum.model.Topic
import org.springframework.stereotype.Component

@Component
class TopicsDto {
    fun convert(topic: Topic) = TopicDto(
            topic.id!!,
            topic.title!!,
            topic.message!!,
            topic.creationDate
    )
}