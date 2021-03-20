package br.com.alura.forum.model

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
    var title: String? = null,
    var message: String? = null,
    @ManyToOne
    var course: Course? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var creationDate = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    var status = TopicStatus.NOT_ANSWERED

    @ManyToOne
    var author: User? = null

    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL], orphanRemoval = true)
    var answers: List<Answer> = arrayListOf()
}
