package com.example.househubadmin.dto.building;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for the page where all building are displayed")
public class BuildingDtoForViewAll {
    private Long id;
    private String image;
    private String name;
    private String address;
    private Integer minPrice;
    private Integer minArea;
}