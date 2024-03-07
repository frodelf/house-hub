package com.example.househubadmin.dto.flat;

import com.example.househubadmin.entity.enums.*;
import lombok.Data;

import java.util.List;

@Data
public class FlatDtoForInformationPage {
    private Long id;
    private List<String> images;
    private Integer price;
    private Integer numberOfRooms;
    private String address;

    private TypeBuilding typeBuilding;
    private LivingCondition livingCondition;
    private Appointment appointment;
    private FoundationDocument foundationDocument;
    private Layout layout;

    private Integer fullArea;
    private Integer kitchenArea;
    private Heating heating;
    private Boolean thereIsBalcony;
    private Boolean thereIsMortgage;
    private Integer agencyCommission;
    private String description;

    private String notaryName;
    private String notaryImage;
}