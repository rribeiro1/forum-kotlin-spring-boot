package io.rafaelribeiro.forum.repository

import io.rafaelribeiro.forum.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long>
