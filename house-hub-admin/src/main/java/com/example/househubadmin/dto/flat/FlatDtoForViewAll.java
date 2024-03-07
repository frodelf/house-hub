package com.example.househubadmin.dto.flat;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FlatDtoForViewAll {
    private Long id;
    private String images;
    private Integer price;
    private Integer numberOfRooms;
    private Integer fullArea;
    private String address;
    private LocalDate updatedDate;
}