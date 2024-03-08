package com.example.househubadmin.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for the page where all users are displayed")
public class UserDtoForViewAll {
    private Long id;
    private String image;
    private String name;
    private String surname;
    private String phone;
    private String email;
}