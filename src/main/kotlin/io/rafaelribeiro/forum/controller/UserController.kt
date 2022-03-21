package io.rafaelribeiro.forum.controller

import io.rafaelribeiro.forum.dtos.user.UserDto
import io.rafaelribeiro.forum.security.ForumUserDetails
import io.rafaelribeiro.forum.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/me")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun me(@AuthenticationPrincipal userDetails: ForumUserDetails): UserDto {
        return UserDto.of(userService.findById(userDetails.id!!))
    }
}
