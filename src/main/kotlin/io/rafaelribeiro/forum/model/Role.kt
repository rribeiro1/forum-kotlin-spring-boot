package io.rafaelribeiro.forum.model

import jakarta.persistence.*

@Entity
class Role(
    val name: String,

    @ManyToMany
    @JoinTable(
        name = "role_privileges",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")]
    )
    val privileges: MutableSet<Privilege> = mutableSetOf()
) : Audit()
