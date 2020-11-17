package com.example.user.security;

import com.example.user.persistence.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Custom implementation of UserDetails object which holds user authentication data.
 * <br/>
 * Authentication data includes username, password and roles.
 */
public class UserAuthContext implements UserDetails {

    private UserEntity userEntity;

    public UserAuthContext(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * Sets user roles in list of <code>{@link GrantedAuthority}</code> class.
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userEntity.getRoles()
                .forEach(roleEntity -> authorities.add(new SimpleGrantedAuthority(roleEntity.getName())));
        return authorities;
    }

    /**
     * Returns password retrieved from database, which will be used by spring security implementation
     * for doing the authentication check.
     * @return
     */
    @Override
    public String getPassword() {
        return userEntity.getBCryptedPassword();
        //return "$2a$04$v/JSaqF22WN0N/IJAw1CTeUqjDpD6lKmdljD0E3owDN0CC6FVbW8i";
       // return "test";
    }

    /**
     * Returns username retrieved from database, which will be used by spring security implementation
     * for doing the authentication check.
     * <br/>
     * Userid from user details table is used as username.
     * @return
     */
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
