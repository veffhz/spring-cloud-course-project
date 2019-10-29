package com.example.usermanagementservice.service.impl;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.repository.UserRepository;
import com.example.usermanagementservice.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username).orElse(null);
    }

    @Override
    public List<String> findUsers(List<Long> idList) {
        return userRepository.findByIds(idList);
    }
}
