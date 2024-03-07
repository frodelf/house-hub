package com.example.househubadmin.controller;

import com.example.househubadmin.jwt.JwtRequest;
import com.example.househubadmin.jwt.JwtResponse;
import com.example.househubadmin.service.impl.AuthorizationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth controller", description = "Auth API")
public class AuthController {
    private final AuthorizationServiceImpl authService;
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "Authorization user")
    @PostMapping("/login")
    public JwtResponse login(@RequestBody(description = "Username and password for authorization")@org.springframework.web.bind.annotation.RequestBody JwtRequest loginRequest){
        return authService.login(loginRequest);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "Update access token")
    @PostMapping("/refresh")
    public JwtResponse refresh(@Parameter(description = "Refresh token") @RequestHeader String refreshToken) {
        return authService.refresh(refreshToken);
    }
}