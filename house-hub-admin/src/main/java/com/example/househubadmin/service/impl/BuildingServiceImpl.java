package com.example.househubadmin.service.impl;

import com.example.househubadmin.dto.building.BuildingDtoForViewAll;
import com.example.househubadmin.entity.Building;
import com.example.househubadmin.entity.Corps;
import com.example.househubadmin.mapper.building.BuildingMapperForViewAll;
import com.example.househubadmin.repository.BuildingRepository;
import com.example.househubadmin.service.BuildingService;
import com.example.househubadmin.service.MinioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {
    private final BuildingRepository buildingRepository;
    private final BuildingMapperForViewAll buildingMapperForViewAll;
    private final MinioService minioService;
    @Override
    public Page<BuildingDtoForViewAll> getAll(Integer page, Integer pageSize, String houseName) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        return buildingMapperForViewAll.toDtoPage(buildingRepository.findAllByNameLike(houseName, pageable), minioService);
    }

    @Override
    public Building getById(Long builderId) {
        return buildingRepository.findById(builderId).orElseThrow(
                ()-> {
                    log.error("Building with id={} not found", builderId);
                    return new EntityNotFoundException("Building with id="+builderId+" not found");
                }
        );
    }

    @Override
    public Map<Long, String> getAllCorpsByBuildingId(Long builderId) {
        Building building = getById(builderId);
        Map<Long, String> corpses = new HashMap<>();
        for (Corps corps : building.getCorps()) {
            corpses.put(corps.getId(), corps.getName());
        }
        return corpses;
    }
}