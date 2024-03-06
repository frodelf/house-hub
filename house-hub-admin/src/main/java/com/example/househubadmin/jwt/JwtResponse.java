package com.example.househubadmin.jwt;

import lombok.Data;

@Data
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
}
