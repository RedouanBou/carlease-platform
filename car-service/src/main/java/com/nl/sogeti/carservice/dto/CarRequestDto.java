package com.nl.sogeti.carservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarRequestDto {

    private String make;
    private String model;
    private String version;
    private int numberOfDoors;
    private String co2Emission;
    private long grossPrice;
    private long nettPrice;

}
