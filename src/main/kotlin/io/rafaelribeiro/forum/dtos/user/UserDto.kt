package io.rafaelribeiro.forum.dtos.user

import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.support.DtoFactory
import java.util.UUID

data class UserDto(
    val id: UUID,
    val name: String,
    val email: String,
    val roles: List<RoleDto>
) {
    companion object : DtoFactory<User, UserDto> {
        override fun of(model: User): UserDto {
            return UserDto(
                id = model.id!!,
                name = model.name,
                email = model.email,
                roles = model.roles.map(RoleDto::of)
            )
        }
    }
}
