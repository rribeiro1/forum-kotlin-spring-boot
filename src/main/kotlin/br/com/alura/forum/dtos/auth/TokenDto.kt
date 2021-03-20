package br.com.alura.forum.dtos.auth

data class TokenDto(
    val token: String,
    val type: String
)
