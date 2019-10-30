package com.example.usermanagementservice.service;

import com.example.usermanagementservice.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User findByUsername(String username);
    List<String> findUsers(List<Long> idList);
}
