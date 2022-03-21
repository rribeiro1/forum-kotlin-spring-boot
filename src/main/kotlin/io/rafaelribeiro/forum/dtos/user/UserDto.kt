package io.rafaelribeiro.forum.dtos.user

import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.support.DtoFactory

data class UserDto(
    val name: String,
    val email: String,
    val roles: List<RoleDto>
) {
    companion object : DtoFactory<User, UserDto> {
        override fun of(model: User): UserDto {
            return UserDto(
                name = model.name,
                email = model.email,
                roles = model.roles.map(RoleDto::of)
            )
        }
    }
}
