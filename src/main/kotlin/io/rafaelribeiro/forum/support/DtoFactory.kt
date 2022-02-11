package io.rafaelribeiro.forum.support

interface DtoFactory<I, O> {
    fun of(model: I): O
}
