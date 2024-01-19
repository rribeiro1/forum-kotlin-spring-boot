package io.rafaelribeiro.forum.repository

import io.rafaelribeiro.forum.model.Privilege
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PrivilegeRepository : JpaRepository<Privilege, UUID>
