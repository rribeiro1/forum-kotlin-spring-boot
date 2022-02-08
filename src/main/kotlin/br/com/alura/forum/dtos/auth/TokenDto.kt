package br.com.alura.forum.dtos.auth

data class TokenDto(
    val token: String,
    val type: String
) {
    companion object {
        fun from(type: String, token: String) = TokenDto(token, type)
    }
}
