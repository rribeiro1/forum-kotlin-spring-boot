package io.rafaelribeiro.forum.model

import javax.persistence.Entity

@Entity
class Course(
    val name: String,
    val category: String
) : Audit()
