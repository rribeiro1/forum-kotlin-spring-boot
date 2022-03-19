package io.rafaelribeiro.forum.repository

import io.rafaelribeiro.forum.model.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    @EntityGraph(
        type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = [
            "roles"
        ]
    )
    fun findByEmail(email: String): User?
}
