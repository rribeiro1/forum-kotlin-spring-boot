package br.com.alura.forum.support

interface DtoFactory<I, O> {
    fun of(model: I): O
}