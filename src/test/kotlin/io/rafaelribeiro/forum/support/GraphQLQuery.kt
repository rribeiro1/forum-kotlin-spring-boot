package io.rafaelribeiro.forum.support

data class GraphQLQuery(
    val query: String,
    val variables: Any? = null
)
