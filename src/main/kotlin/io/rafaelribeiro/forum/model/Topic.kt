package io.rafaelribeiro.forum.model

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Topic(
    var title: String,
    var message: String,
    @ManyToOne
    var course: Course,
    @ManyToOne
    var author: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var creationDate = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    var status = TopicStatus.NOT_ANSWERED

    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL], orphanRemoval = true)
    var answers: List<Answer> = arrayListOf()
}
