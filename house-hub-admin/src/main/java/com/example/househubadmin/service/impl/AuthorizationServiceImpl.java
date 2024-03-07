package com.example.househubadmin.service.impl;

import com.example.househubadmin.entity.users.Admin;
import com.example.househubadmin.jwt.JwtRequest;
import com.example.househubadmin.jwt.JwtResponse;
import com.example.househubadmin.jwt.JwtTokenProvider;
import com.example.househubadmin.service.AdminService;
import com.example.househubadmin.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthorizationServiceImpl implements AuthorizationService {
    private final AuthenticationManager authenticationManager;
    private final AdminService adminService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        Admin admin = adminService.getByEmail(loginRequest.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(admin.getId(), admin.getEmail()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(admin.getId(), admin.getEmail()));
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}