package com.nl.sogeti.leaserateservice.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LeaseRate {
    private BigDecimal mileage;
    private Integer duration;
    private BigDecimal interestRate;
    private BigDecimal nettPrice;
}
