package com.matheus.demo_park_api.jwt;

import com.matheus.demo_park_api.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class JwtUserDetails implements UserDetails {
    private final Usuario usuario;

    public JwtUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna uma única authority para o papel do usuário
        return Collections.singletonList((GrantedAuthority) () -> usuario.getRole().name());
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    // Expor o ID do usuário
    public Long getId() {
        return usuario.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
