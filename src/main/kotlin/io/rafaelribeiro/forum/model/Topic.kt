package io.rafaelribeiro.forum.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

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
