package com.example.househubadmin.dto.notary;

import lombok.Data;

@Data
public class NotaryDtoForViewAll {
    private Long id;
    private String image;
    private String name;
    private String surname;
    private String phone;
    private String email;
}