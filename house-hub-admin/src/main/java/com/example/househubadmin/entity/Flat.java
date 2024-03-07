package com.example.househubadmin.entity;

import com.example.househubadmin.entity.enums.*;
import com.example.househubadmin.entity.users.Notary;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numberOfRooms;
    private Integer fullArea;
    private Integer kitchenArea;
    private Boolean thereIsBalcony;
    private Boolean thereIsMortgage;
    private String calculationOptions;
    private Integer agencyCommission;
    private String description;
    private String reason;
    private Integer price;
    private LocalDate updatedDate;

    private StatusState statusState;
    private Appointment appointment;
    private FoundationDocument foundationDocument;
    private LivingCondition livingCondition;
    private Layout layout;
    private Heating heating;

    @ElementCollection
    private List<String> images;
    @ManyToOne
    @JoinColumn(name = "corps_id", referencedColumnName = "id")
    private Corps corps;
    @ManyToOne
    @JoinColumn(name = "notary_id", referencedColumnName = "id")
    private Notary notary;
}