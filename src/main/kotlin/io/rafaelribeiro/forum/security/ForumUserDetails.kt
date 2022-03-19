package io.rafaelribeiro.forum.security

import io.rafaelribeiro.forum.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class ForumUserDetails(val user: User) : UserDetails {
    val id: Long? = user.id

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = user.grantedAuthorities
    override fun getPassword(): String = user.pass
    override fun getUsername(): String = user.email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}
