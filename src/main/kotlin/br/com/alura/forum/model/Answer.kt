package br.com.alura.forum.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var message: String? = null

    @ManyToOne
    var topic: Topic? = null
    var creationDate: LocalDateTime = LocalDateTime.now()

    @ManyToOne
    var author: User? = null
    var solution: Boolean? = false
}