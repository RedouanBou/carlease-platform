package com.nl.sogeti.carservice.service;

import com.nl.sogeti.carservice.entity.Car;
import com.nl.sogeti.carservice.exceptions.CarNotFoundException;
import com.nl.sogeti.carservice.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    private CarRepository _repository;


    public List<Car> getAll() {
        return this._repository.findAll();
    }

    public Car getById(long id) {
        return this._repository.findById(id)
                .orElseThrow(CarNotFoundException::new);
    }

    public Car create(Car car) {
        return this._repository.save(car);
    }

    public Car updateById(Car car) {
        var existingCar = this._repository.findById(car.getId())
                .orElseThrow(CarNotFoundException::new);

        var updatedCar = Car.builder()
                .id(existingCar.getId())
                .make(car.getMake())
                .model(car.getModel())
                .version(car.getVersion())
                .numberOfDoors(car.getNumberOfDoors())
                .co2Emission(car.getCo2Emission())
                .grossPrice(car.getGrossPrice())
                .nettPrice(car.getNettPrice())
                .build();

        return this._repository.save(updatedCar);
    }

    public void deleteById(long id) {
        var car = this._repository.findById(id)
                .orElseThrow(CarNotFoundException::new);

        this._repository.delete(car);
    }
}
