package io.rafaelribeiro.forum.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.persistence.*

@Entity
@Table(name = "author")
class User(
    val name: String,
    val email: String,
    val pass: String
) : Audit() {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "author_roles",
        joinColumns = [JoinColumn(name = "author_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: MutableSet<Role> = mutableSetOf()

    @get:Transient
    val grantedAuthorities: MutableCollection<GrantedAuthority>
        get() = getAuthorities()

    private fun getAuthorities(): MutableCollection<GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        val privileges = getPrivileges(roles)
        for (privilege in privileges) {
            authorities.add(SimpleGrantedAuthority(privilege))
        }
        return authorities
    }

    private fun getPrivileges(roles: Collection<Role>): MutableCollection<String> {
        val privileges: MutableCollection<String> = ArrayList()
        val collection: MutableCollection<Privilege> = ArrayList()
        for (role in roles) {
            privileges.add(role.name)
            collection.addAll(role.privileges)
        }
        for (item in collection) {
            privileges.add(item.name)
        }
        return privileges
    }
}
