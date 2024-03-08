package com.example.househubadmin.entity;

import com.example.househubadmin.entity.enums.*;
import com.example.househubadmin.entity.users.Builder;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;
    private Integer minPrice;
    private Integer pricePerSquareMeter;
    private Integer minArea;
    private Integer maxArea;
    private String distanceToSea;
    private String ceilingHeight;
    private String drawingUp;
    private String calculationOptions;
    private String aim;
    private String amountSum;

    private StatusState statusState;
    private StatusBuilding statusBuilding;
    private TypeBuilding typeBuilding;
    private Level level;
    private ConstructionTechnologies constructionTechnologies;
    private Territory territory;
    private Electricity electricity;
    private Gas gas;
    private Heating heating;
    private Sewage sewage;
    private WaterSupply waterSupply;

    @ElementCollection
    private List<String> images;
    @ElementCollection
    private List<String> documents;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Corps> corps;
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<News> newses;
    @ManyToOne
    @JoinColumn(name = "builder_id", referencedColumnName = "id")
    private Builder builder;
}