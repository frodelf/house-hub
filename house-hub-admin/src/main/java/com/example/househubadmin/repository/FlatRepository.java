package com.example.househubadmin.repository;

import com.example.househubadmin.entity.Flat;
import com.example.househubadmin.entity.enums.StatusState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FlatRepository extends JpaRepository<Flat, Long> {
    Page<Flat> findAllByCorpsId(Long corpsId, Pageable pageable);

    Page<Flat> findAllByStatusState(StatusState statusState, Pageable pageable);
}