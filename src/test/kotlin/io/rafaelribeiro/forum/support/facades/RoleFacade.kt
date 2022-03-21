package io.rafaelribeiro.forum.support.facades

import io.rafaelribeiro.forum.model.Privilege
import io.rafaelribeiro.forum.model.Role
import io.rafaelribeiro.forum.repository.PrivilegeRepository
import io.rafaelribeiro.forum.repository.RoleRepository
import org.springframework.stereotype.Component

@Component
class RoleFacade(
    private val roleRepository: RoleRepository,
    private val privilegeRepository: PrivilegeRepository
) {
    fun createRoleWithPrivileges(name: String, privileges: List<String>): Role {
        val savedPrivileges = privileges.map { privilegeRepository.save(Privilege(it)) }.toMutableSet()
        return roleRepository.save(Role(name = name, privileges = savedPrivileges))
    }

    fun resetDatabase() {
        privilegeRepository.deleteAll()
        roleRepository.deleteAll()
    }
}
