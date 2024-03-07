package com.example.househubadmin.service.impl;

import com.example.househubadmin.repository.UserRepository;
import com.example.househubadmin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public boolean validPhone(String telephone) {
        log.info("UserServiceImpl-validTelephone start");
        boolean res = userRepository.existsByPhone(telephone);
        log.info("UserServiceImpl-validTelephone successfully");
        return res;
    }
    @Override
    public boolean validEmail(String email) {
        log.info("UserServiceImpl-validEmail start");
        boolean res = userRepository.existsByEmail(email);
        log.info("UserServiceImpl-validEmail successfully");
        return res;
    }
}
