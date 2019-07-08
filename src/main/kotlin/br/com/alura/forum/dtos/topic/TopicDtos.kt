package br.com.alura.forum.dtos.topic

import br.com.alura.forum.model.Course
import br.com.alura.forum.model.Topic
import org.springframework.stereotype.Component

@Component
class TopicDtos {
    fun convertToDto(topic: Topic) = TopicDto(
            topic.id!!,
            topic.title!!,
            topic.message!!,
            topic.creationDate
    )

    fun convertToEntity(createTopicDto: CreateTopicDto, course: Course) = Topic(
            createTopicDto.title,
            createTopicDto.message,
            course
    )
}