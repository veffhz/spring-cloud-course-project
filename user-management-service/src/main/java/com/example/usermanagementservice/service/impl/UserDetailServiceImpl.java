package com.example.usermanagementservice.service.impl;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName).orElse(null);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(userName);
        }

        Set<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority(user.getRole().name())
        );

        return new org.springframework.security.core.userdetails.User(userName, user.getPassword(), authorities);
    }
}
