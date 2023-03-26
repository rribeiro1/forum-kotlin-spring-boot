package io.rafaelribeiro.forum.model

import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

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
