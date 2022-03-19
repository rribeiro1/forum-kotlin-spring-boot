package io.rafaelribeiro.forum.model

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
data class Topic(
    val title: String,
    val message: String,
    @ManyToOne
    val course: Course,
    @ManyToOne
    val author: User
) : Audit() {
    val creationDate: LocalDateTime = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    val status = TopicStatus.NOT_ANSWERED

    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL], orphanRemoval = true)
    val answers: List<Answer> = arrayListOf()
}
