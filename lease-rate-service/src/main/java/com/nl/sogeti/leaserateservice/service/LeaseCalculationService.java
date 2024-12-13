package com.nl.sogeti.leaserateservice.service;

import com.nl.sogeti.leaserateservice.dto.LeaseCalculationResponseDto;
import com.nl.sogeti.leaserateservice.models.LeaseRate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@AllArgsConstructor
public class LeaseCalculationService {

    public LeaseCalculationResponseDto calculate(LeaseRate leaseCalculation) {
        BigDecimal monthlyMileageCost = leaseCalculation.getMileage()
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(leaseCalculation.getDuration()));

        BigDecimal interestCost = leaseCalculation.getInterestRate()
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                .multiply(leaseCalculation.getNettPrice())
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);

        BigDecimal leaserate = monthlyMileageCost
                .divide(leaseCalculation.getNettPrice(), 4, RoundingMode.HALF_UP)
                .add(interestCost)
                .setScale(2, RoundingMode.HALF_UP);

        LeaseCalculationResponseDto response = new LeaseCalculationResponseDto(leaserate);
        return response;
    }

}