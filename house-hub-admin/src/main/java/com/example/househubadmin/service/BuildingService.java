package com.example.househubadmin.service;

import com.example.househubadmin.dto.building.BuildingDtoForViewAll;
import com.example.househubadmin.entity.Building;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface BuildingService {
    Page<BuildingDtoForViewAll> getAll(Integer page, Integer pageSize, String houseName);
    Building getById(Long builderId);
    Map<Long, String> getAllCorpsByBuildingId(Long builderId);
}