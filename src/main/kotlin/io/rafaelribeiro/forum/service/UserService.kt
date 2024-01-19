package io.rafaelribeiro.forum.service

import io.rafaelribeiro.forum.model.User
import io.rafaelribeiro.forum.repository.UserRepository
import io.rafaelribeiro.forum.support.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserService(private val userRepository: UserRepository) {
    @Transactional(readOnly = true)
    fun findById(userId: UUID): User = userRepository.findById(userId).orElseThrow {
        throw ResourceNotFoundException(userId.toString(), User::class)
    }
}
