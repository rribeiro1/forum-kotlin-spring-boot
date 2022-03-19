package io.rafaelribeiro.forum.model

import javax.persistence.*

@Entity
class Privilege(
    val name: String
) : Audit()
