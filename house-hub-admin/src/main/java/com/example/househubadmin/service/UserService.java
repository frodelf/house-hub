package com.example.househubadmin.service;

import com.example.househubadmin.entity.users.User;

public interface UserService {
    boolean validPhone(String telephone);
    boolean validEmail(String email);
    User getById(Long id);
    void save(User user);
}