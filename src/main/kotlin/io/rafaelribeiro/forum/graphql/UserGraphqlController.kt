package io.rafaelribeiro.forum.graphql

import io.rafaelribeiro.forum.dtos.user.UserDto
import io.rafaelribeiro.forum.security.ForumUserDetails
import io.rafaelribeiro.forum.service.UserService
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller

@Controller
class UserGraphqlController(
    private val userService: UserService
) {
    @QueryMapping
    fun user(@AuthenticationPrincipal userDetails: ForumUserDetails): UserDto {
        return UserDto.of(userService.findById(userDetails.id!!))
    }
}
