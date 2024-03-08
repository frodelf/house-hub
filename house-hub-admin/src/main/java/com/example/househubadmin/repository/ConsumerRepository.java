package com.example.househubadmin.repository;

import com.example.househubadmin.entity.enums.StatusUser;
import com.example.househubadmin.entity.users.Consumer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    Page<Consumer> findAllByNameLikeAndStatus(String name, StatusUser status, Pageable pageable);
}
