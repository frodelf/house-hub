package com.example.househubadmin.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Data
// TODO: 07.03.2024 verify removal
public class Corps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;
    @OneToMany(mappedBy = "corps")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<Flat> flats;
}