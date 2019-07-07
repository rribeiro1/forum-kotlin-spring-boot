package br.com.alura.forum.model

import javax.persistence.*

@Entity
@Table(name = "author")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var name: String? = null
    var email: String? = null
    var password: String? = null

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
        val other = obj as User?
        if (id == null) {
            if (other!!.id != null)
                return false
        } else if (id != other!!.id)
            return false
        return true
    }
}