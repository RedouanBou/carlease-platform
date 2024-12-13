package com.nl.sogeti.leaserateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaseCalculationRequestDto {

    private BigDecimal mileage;
    private Integer duration;
    private BigDecimal interestRate;
    private BigDecimal nettPrice;

}
