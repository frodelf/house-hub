package com.example.househubadmin;

import com.example.househubadmin.entity.users.Admin;
import com.example.househubadmin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {
    private final AdminRepository adminRepository;
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(!adminRepository.existsById(1L)){
            Admin admin = new Admin();
            admin.setId(1L);
            admin.setEmail("admin@gmail.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            admin.setName("Admin");
            adminRepository.save(admin);
        }
    }
}