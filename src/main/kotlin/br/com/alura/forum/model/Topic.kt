package br.com.alura.forum.model

import java.time.LocalDateTime
import javax.persistence.*

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

	override fun hashCode(): Int {
		val prime = 31
		var result = 1
		result = prime * result + if (id == null) 0 else id!!.hashCode()
		return result
	}

	override fun equals(obj: Any?): Boolean {
		if (this === obj)
			return true
		if (obj == null)
			return false
		if (javaClass != obj.javaClass)
			return false
		val other = obj as Topic?
		if (id == null) {
			if (other!!.id != null)
				return false
		} else if (id != other!!.id)
			return false
		return true
	}

}