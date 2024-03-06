package com.example.househubadmin.service;

import com.example.househubadmin.entity.users.Admin;

public interface AdminService {
    Admin getByEmail(String email);

    Admin getById(Long id);
}