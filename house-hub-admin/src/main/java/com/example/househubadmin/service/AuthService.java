package com.example.househubadmin.service;

import com.example.househubadmin.jwt.JwtRequest;
import com.example.househubadmin.jwt.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
}