package com.nl.sogeti.leaserateservice.controller;

import com.nl.sogeti.leaserateservice.dto.LeaseCalculationRequestDto;
import com.nl.sogeti.leaserateservice.dto.LeaseCalculationResponseDto;
import com.nl.sogeti.leaserateservice.models.LeaseRate;
import com.nl.sogeti.leaserateservice.service.LeaseCalculationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lease-rate")
public class LeaseCalculationController {

    private final LeaseCalculationService _service;
    private final ModelMapper _mapper;

    @PostMapping("/calculate")
    public ResponseEntity<LeaseCalculationResponseDto> calculateLeaseReate(@RequestBody @Valid LeaseCalculationRequestDto leaseCalculationRequestDto) {
        var mappedLeaseRate = this._mapper.map(leaseCalculationRequestDto, LeaseRate.class);

        var response = this._service.calculate(mappedLeaseRate);

        return ResponseEntity.ok(response);
    }
}
