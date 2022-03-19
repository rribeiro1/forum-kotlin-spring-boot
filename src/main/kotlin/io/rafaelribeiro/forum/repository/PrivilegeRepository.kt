package io.rafaelribeiro.forum.repository

import io.rafaelribeiro.forum.model.Privilege
import org.springframework.data.jpa.repository.JpaRepository

interface PrivilegeRepository : JpaRepository<Privilege, Long>
