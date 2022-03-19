package io.rafaelribeiro.forum.model

import javax.persistence.*

@Entity
class Role(
    val name: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "role_privileges",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")]
    )
    val privileges: MutableSet<Privilege> = mutableSetOf()
) : Audit()
