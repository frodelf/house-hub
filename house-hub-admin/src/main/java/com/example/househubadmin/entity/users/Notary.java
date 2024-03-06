package com.example.househubadmin.entity.users;

import com.example.househubadmin.entity.Flat;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Notary extends User{
    @OneToMany(mappedBy = "notary")
    private List<Flat> flat;
    @ManyToMany(mappedBy = "notaries")
    private List<Consumer> consumer;
}