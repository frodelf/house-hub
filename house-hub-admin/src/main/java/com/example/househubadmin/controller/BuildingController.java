package com.example.househubadmin.controller;

import com.example.househubadmin.dto.building.BuildingDtoForViewAll;
import com.example.househubadmin.service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/building")
@RequiredArgsConstructor
@Tag(name = "Building controller", description = "Building API")
public class BuildingController {
    private final BuildingService buildingService;
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Operation(summary = "The request to get all buildings")
    @GetMapping("/get-all")
    public ResponseEntity<Page<BuildingDtoForViewAll>> getAllBuildings(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String houseName){
        return ResponseEntity.ok(buildingService.getAll(page, pageSize, houseName));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Entity not found exception."),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Operation(summary = "The request to get all corps by building id")
    @GetMapping("/get-all-corps-by-building-id/{builderId}")
    public ResponseEntity<Map<Long, String>> getAllCorps(@PathVariable Long builderId){
        return ResponseEntity.ok(buildingService.getAllCorpsByBuildingId(builderId));
    }
}