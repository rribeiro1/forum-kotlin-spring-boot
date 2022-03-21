package io.rafaelribeiro.forum.repository

import io.rafaelribeiro.forum.model.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    @EntityGraph(value = "UserRolesAndPrivileges")
    fun findByEmail(email: String): User?

    @EntityGraph(value = "UserRolesAndPrivileges")
    override fun findById(id: Long): Optional<User>
}
