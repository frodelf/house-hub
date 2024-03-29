package com.example.househubadmin.controller;

import com.example.househubadmin.jwt.JwtRequest;
import com.example.househubadmin.jwt.JwtResponse;
import com.example.househubadmin.service.impl.AuthorizationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth controller", description = "Auth API")
public class AuthController {
    private final AuthorizationServiceImpl authService;
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Operation(summary = "Authorization user")
    @PostMapping("/login")
    public JwtResponse login(@RequestBody(description = "Username and password for authorization") @org.springframework.web.bind.annotation.RequestBody JwtRequest loginRequest){
        return authService.login(loginRequest);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Operation(summary = "Update access token")
    @PostMapping("/refresh")
    public JwtResponse refresh(@Parameter(description = "Refresh token") @RequestHeader String refreshToken) {
        return authService.refresh(refreshToken);
    }
}