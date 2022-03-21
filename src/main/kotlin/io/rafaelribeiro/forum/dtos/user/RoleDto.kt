package io.rafaelribeiro.forum.dtos.user

import io.rafaelribeiro.forum.model.Role
import io.rafaelribeiro.forum.support.DtoFactory

data class RoleDto(
    val name: String,
    val privileges: List<PrivilegeDto>
) {
    companion object : DtoFactory<Role, RoleDto> {
        override fun of(model: Role): RoleDto {
            return RoleDto(
                name = model.name,
                privileges = model.privileges.map(PrivilegeDto::of)
            )
        }
    }
}
