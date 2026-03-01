package com.example.userservice.oauth2;

import com.example.userservice.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Entity that maps User object into UserDetails type object.
public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO - Map Role entity with GrantedAuthority (Using CustomGrantedAuthority).
        List<CustomGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new CustomGrantedAuthority(role))
                .toList();
        return authorities;
//        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}


/*

 <<GranterAuthority>> ---> Role

 */