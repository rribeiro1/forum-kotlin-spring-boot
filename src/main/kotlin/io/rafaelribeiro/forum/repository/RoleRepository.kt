package io.rafaelribeiro.forum.repository

import io.rafaelribeiro.forum.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoleRepository : JpaRepository<Role, UUID>
