package br.com.alura.forum.repository

import br.com.alura.forum.model.User
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
}
