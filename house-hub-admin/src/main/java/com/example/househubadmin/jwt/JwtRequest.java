package com.example.househubadmin.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JwtRequest {
    @Schema(description = "email", example = "admin@gmail.com")
    private String username;
    @Schema(description = "password", example = "admin")
    private String password;
}