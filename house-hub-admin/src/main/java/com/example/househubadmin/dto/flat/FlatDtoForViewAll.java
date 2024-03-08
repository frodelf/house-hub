package com.example.househubadmin.dto.flat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "DTO for the page where all flats are displayed")
public class FlatDtoForViewAll {
    private Long id;
    private String images;
    private Integer price;
    private Integer numberOfRooms;
    private Integer fullArea;
    private String address;
    private LocalDate updatedDate;
}