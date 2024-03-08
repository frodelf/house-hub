package com.example.househubadmin.service;

import com.example.househubadmin.dto.user.UserDtoForViewAll;
import com.example.househubadmin.entity.enums.StatusUser;
import org.springframework.data.domain.Page;

public interface ConsumerService {
    Page<UserDtoForViewAll> getAll(Integer page, Integer pageSize, String consumerName, StatusUser statusUser);
    void changeStatusById(Long consumerId, StatusUser statusUser);
}