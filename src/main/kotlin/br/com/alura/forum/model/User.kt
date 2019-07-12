package br.com.alura.forum.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "author")
class User : UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var name: String? = null
    var email: String? = null
    var pass: String? = null

    @ManyToMany(fetch = FetchType.EAGER)
    var profiles: MutableList<Profile> = mutableListOf()

    override fun getUsername() = this.email!!
    override fun getPassword() = this.pass!!
    override fun isEnabled() = true
    override fun isCredentialsNonExpired() = true
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> { return profiles }
}