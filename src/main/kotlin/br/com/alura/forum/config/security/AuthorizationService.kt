package br.com.alura.forum.config.security

import br.com.alura.forum.model.Topic
import br.com.alura.forum.model.User
import org.springframework.stereotype.Service

@Service
class AuthorizationService {
    fun topicBelongsToUser(user: User, topic: Topic): Boolean {
        println(topic.author.id == user.id)
        return topic.author.id == user.id
    }
}
