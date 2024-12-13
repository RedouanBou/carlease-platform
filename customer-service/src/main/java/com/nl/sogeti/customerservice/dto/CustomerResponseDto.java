package com.nl.sogeti.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {
    private long id;
    private String name;
    private String street;
    private int houseNumber;
    private String zipCode;
    private String place;
    private String emailAddress;
    private String phoneNumber;
}
