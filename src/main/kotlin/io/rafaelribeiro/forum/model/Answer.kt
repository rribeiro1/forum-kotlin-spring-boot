package io.rafaelribeiro.forum.model

import java.time.LocalDateTime
import javax.persistence.Entity
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
) : Audit()
