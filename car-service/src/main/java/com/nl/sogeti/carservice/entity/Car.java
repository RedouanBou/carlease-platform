package com.nl.sogeti.carservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String make;

    @NotNull
    private String model;

    @NotNull
    private String version;

    @NotNull
    private int numberOfDoors;

    @NotNull
    private String co2Emission;

    @NotNull
    private long grossPrice;

    @NotNull
    private long nettPrice;
}
