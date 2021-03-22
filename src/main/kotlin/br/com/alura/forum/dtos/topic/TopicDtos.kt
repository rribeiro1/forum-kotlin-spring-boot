package br.com.alura.forum.dtos.topic

import br.com.alura.forum.model.Topic
import org.springframework.stereotype.Component

@Component
class TopicDtos {
    fun apply(target: Topic, input: TopicUpdateDto) = target.apply {
        input.title.let { title = it }
        input.message.let { message = it }
    }
}
