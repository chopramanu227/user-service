package com.example.user.security;

import com.example.user.persistence.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserAuthContext implements UserDetails {

    private UserEntity userEntity;

    public UserAuthContext(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userEntity.getRoles()
                .forEach(roleEntity -> authorities.add(new SimpleGrantedAuthority(roleEntity.getName())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getBCryptedPassword();
        //return "$2a$04$v/JSaqF22WN0N/IJAw1CTeUqjDpD6lKmdljD0E3owDN0CC6FVbW8i";
       // return "test";
    }

    @Override
    public String getUsername() {
        return String.valueOf(userEntity.getUserId());
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
