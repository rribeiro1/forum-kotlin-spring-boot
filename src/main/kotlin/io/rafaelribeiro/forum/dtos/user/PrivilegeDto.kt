package io.rafaelribeiro.forum.dtos.user

import io.rafaelribeiro.forum.model.Privilege
import io.rafaelribeiro.forum.support.DtoFactory

data class PrivilegeDto(val name: String) {
    companion object : DtoFactory<Privilege, PrivilegeDto> {
        override fun of(model: Privilege): PrivilegeDto {
            return PrivilegeDto(model.name)
        }
    }
}
