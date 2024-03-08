package com.example.househubadmin.dto.user;

import lombok.Data;

@Data
public class UserDtoForViewAll {
    private Long id;
    private String image;
    private String name;
    private String surname;
    private String phone;
    private String email;
}