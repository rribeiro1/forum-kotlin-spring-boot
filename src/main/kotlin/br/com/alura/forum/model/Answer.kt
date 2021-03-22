package br.com.alura.forum.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Answer(
    val message: String,

    @ManyToOne
    val topic: Topic,
    val creationDate: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    val author: User,

    val solution: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
