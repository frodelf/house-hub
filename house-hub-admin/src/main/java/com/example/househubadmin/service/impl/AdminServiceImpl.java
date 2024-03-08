package com.example.househubadmin.service.impl;

import com.example.househubadmin.entity.users.Admin;
import com.example.househubadmin.repository.AdminRepository;
import com.example.househubadmin.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    @Override
    public Admin getByEmail(String email) {
        return adminRepository.findByEmail(email).orElseThrow(
                ()-> {
                    log.error("Admin with email={} not found", email);
                    return new EntityNotFoundException("Admin with email="+email+" not found");
                }
        );
    }
}