package com.example.househubadmin.repository;

import com.example.househubadmin.entity.users.Notary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaryRepository extends JpaRepository<Notary, Long> {
}