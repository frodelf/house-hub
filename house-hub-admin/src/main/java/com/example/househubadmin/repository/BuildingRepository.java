package com.example.househubadmin.repository;

import com.example.househubadmin.entity.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    Page<Building> findAllByNameLike(String name, Pageable pageable);
}
