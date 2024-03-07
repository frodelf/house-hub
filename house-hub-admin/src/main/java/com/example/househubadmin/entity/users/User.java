package com.example.househubadmin.entity.users;

import com.example.househubadmin.entity.enums.StatusState;
import com.example.househubadmin.entity.enums.StatusUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private StatusUser status;
}