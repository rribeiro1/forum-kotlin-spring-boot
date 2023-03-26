package io.rafaelribeiro.forum.model

import jakarta.persistence.*

@Entity
class Privilege(
    val name: String
) : Audit()
