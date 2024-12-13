package com.nl.sogeti.carservice.controllers;

import com.nl.sogeti.carservice.dto.CarRequestDto;
import com.nl.sogeti.carservice.dto.CarResponseDto;
import com.nl.sogeti.carservice.entity.Car;
import com.nl.sogeti.carservice.service.CarService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cars")
public class CarController {

    private final CarService _service;
    private final ModelMapper _mapper;

    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getCars() {
        List<Car> cars = this._service.getAll();

        List<CarResponseDto> response = cars.stream()
                .map(this::mapCar)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    private CarResponseDto mapCar(Car car) {
        return CarResponseDto.builder()
                .id(car.getId())
                .make(car.getMake())
                .model(car.getModel())
                .version(car.getVersion())
                .numberOfDoors(car.getNumberOfDoors())
                .co2Emission(car.getCo2Emission())
                .grossPrice(car.getGrossPrice())
                .nettPrice(car.getNettPrice())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> getCar(@PathVariable long id) {
        var car = this._service.getById(id);

        var response = this._mapper.map(car, CarResponseDto.class);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CarResponseDto> createCar(@RequestBody @Valid CarRequestDto carDto) {
        var mappedCar = this._mapper.map(carDto, Car.class);

        var createdCar = this._service.create(mappedCar);

        var response = this._mapper.map(createdCar, CarResponseDto.class);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDto> updateCar(@PathVariable long id, @RequestBody @Valid CarRequestDto carDto) {
        var mappedCar = this._mapper.map(carDto, Car.class);

        mappedCar.setId(id);

        var updatedCar = this._service.updateById(mappedCar);

        var response = this._mapper.map(updatedCar, CarResponseDto.class);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable long id) {
        this._service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
