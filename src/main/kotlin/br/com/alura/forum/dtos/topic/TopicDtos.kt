package br.com.alura.forum.dtos.topic

import br.com.alura.forum.dtos.answer.AnswerDtos
import br.com.alura.forum.model.Course
import br.com.alura.forum.model.Topic
import org.springframework.stereotype.Component

@Component
class TopicDtos(
        private val answerDtos: AnswerDtos
) {
    fun convertToDto(topic: Topic) = TopicDto(
            topic.id!!,
            topic.title!!,
            topic.message!!,
            topic.creationDate
    )

    fun convertToEntity(topicCreateDto: TopicCreateDto, course: Course) = Topic(
            topicCreateDto.title,
            topicCreateDto.message,
            course
    )

    fun convertToDetailDto(topic: Topic) = TopicDetailDto(
            topic.id!!,
            topic.title!!,
            topic.message!!,
            topic.creationDate,
            topic.author!!.name!!,
            topic.status,
            topic.answers.map { answerDtos.convert(it) }
    )

    fun apply(target: Topic, input: TopicUpdateDto) = target.apply {
        input.title.let { title = it }
        input.message.let { message = it }
    }
}