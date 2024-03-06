package com.example.househubadmin.entity.users;

import com.example.househubadmin.entity.Building;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Builder extends User{
    @OneToMany(mappedBy = "builder")
    private List<Building> buildings;
}