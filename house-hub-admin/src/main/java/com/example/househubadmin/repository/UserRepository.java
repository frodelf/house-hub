package com.example.househubadmin.repository;

import com.example.househubadmin.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhone(String telephone);

    boolean existsByEmail(String email);
}
