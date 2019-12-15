package com.example.usermanagementservice.service;

import com.example.usermanagementservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findByUsername(String username);
    List<String> findUsers(List<Long> idList);
}
