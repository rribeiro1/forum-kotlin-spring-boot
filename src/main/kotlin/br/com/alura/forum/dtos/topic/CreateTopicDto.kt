package br.com.alura.forum.dtos.topic

import br.com.alura.forum.model.Course
import br.com.alura.forum.model.Topic

data class CreateTopicDto (
        val title: String,
        val message: String,
        val courseName: String
) {
    fun convert(createTopicDto: CreateTopicDto, course: Course) = Topic(
            createTopicDto.title,
            createTopicDto.message,
            course
    )
}