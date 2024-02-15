package com.example.labxpert.config.security;

import com.example.labxpert.dtos.UserDto;
import com.example.labxpert.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        UserDto user = userService.loadUserByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User Not Found");
        Collection<GrantedAuthority>     = new ArrayList<>();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        authorities.add(authority);

        return new User(user.getEmail(), user.getPassword(), authorities);
    }

}
