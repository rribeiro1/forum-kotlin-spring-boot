package io.rafaelribeiro.forum.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@NamedEntityGraph(
    name = "UserRolesAndPrivileges",
    attributeNodes = [NamedAttributeNode(value = "roles", subgraph = "privileges")],
    subgraphs = [
        NamedSubgraph(
            name = "privileges",
            attributeNodes = [NamedAttributeNode(value = "privileges")]
        )
    ]
)
@Table(name = "author")
class User(
    val name: String,
    val email: String,
    val pass: String
) : Audit(), UserDetails {
    @ManyToMany
    @JoinTable(
        name = "author_roles",
        joinColumns = [JoinColumn(name = "author_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: MutableSet<Role> = mutableSetOf()

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

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        val privileges = getPrivileges(roles)
        for (privilege in privileges) {
            authorities.add(SimpleGrantedAuthority(privilege))
        }
        return authorities
    }

    override fun getPassword() = pass
    override fun getUsername() = email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}
