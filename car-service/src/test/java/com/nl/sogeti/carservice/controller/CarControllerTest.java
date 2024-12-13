package com.nl.sogeti.carservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.sogeti.carservice.TestConfig;
import com.nl.sogeti.carservice.controllers.CarController;
import com.nl.sogeti.carservice.dto.CarRequestDto;
import com.nl.sogeti.carservice.entity.Car;
import com.nl.sogeti.carservice.exceptions.CarNotFoundException;
import com.nl.sogeti.carservice.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CarService _service;

    @Test
    void whenGetCarsThenReturnListOfCars() throws Exception {
        // Given
        var cars = Arrays.asList(
                createCarWithId(1L),
                createCarWithId(2L)
        );

        when(_service.getAll()).thenReturn(cars);

        // When
        mockMvc.perform(get("/api/cars")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(cars.size()))
                .andExpect(jsonPath("*.id").isNotEmpty());
    }

    private static Car createCarWithId(long id) {
        return Car.builder()
                .id(id)
                .make("Toyota")
                .model("Any")
                .version("2021")
                .numberOfDoors(4)
                .co2Emission("120")
                .grossPrice(18000)
                .nettPrice(18000)
                .build();
    }

    @Test
    void whenNoCarsThenGetCarsReturnEmptyList() throws Exception {
        // Given
        when(_service.getAll()).thenReturn(Collections.emptyList());

        // When
        mockMvc.perform(get("/api/cars")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenCarIdExistsThenGetCarRespondsWithOk() throws Exception {
        // Given
        long carId = 1L;

        // When
        when(_service.getById(carId)).thenReturn(new Car());

        mockMvc.perform(get("/api/cars/{id}", carId)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenCarDoesNotExistThenGetCarRespondsWithNotFound() throws Exception {
        // Given
        long carId = 10L;

        when(_service.getById(carId)).thenThrow(new CarNotFoundException());

        // When
        mockMvc.perform(get("/api/cars/{id}", carId)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenCreateCarThenRespondWithOk() throws Exception {
        // Given
        var car = CarRequestDto.builder()
                .make("Toyota")
                .model("Any")
                .version("2021")
                .numberOfDoors(4)
                .co2Emission("120")
                .grossPrice(18000)
                .nettPrice(18000)
                .build();

        when(_service.create(any())).thenReturn(new Car());

        // When
        mockMvc.perform(post("/api/cars")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk());
    }

    @Test
    void whenCreateCarInvalidThenRespondWithBadRequest() throws Exception {
        // When
        mockMvc.perform(post("/api/cars")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUpdateCarThenRespondWithOk() throws Exception {
        // Given
        var car = CarRequestDto.builder()
                .make("Toyota")
                .model("Any")
                .version("2021")
                .numberOfDoors(4)
                .co2Emission("120")
                .grossPrice(18000)
                .nettPrice(18000)
                .build();

        var mockedCar = new Car();
        when(_service.updateById(any())).thenReturn(mockedCar);

        // When
        mockMvc.perform(put("/api/cars/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk());
    }

    @Test
    void whenUpdateCarInvalidThenRespondWithBadRequest() throws Exception {
        when(_service.create(any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not all fields are specified"));

        // When
        mockMvc.perform(put("/api/cars/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCarDoesNotExistThenUpdateCarRespondsWithNotFound() throws Exception {
        // given
        var car = CarRequestDto.builder()
                .make("Toyota")
                .model("Any")
                .version("2021")
                .numberOfDoors(4)
                .co2Emission("120")
                .grossPrice(18000)
                .nettPrice(18000)
                .build();

        when(_service.updateById(any())).thenThrow(new CarNotFoundException());

        // When
        mockMvc.perform(put("/api/cars/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenDeleteCarThenRespondWithNoContent() throws Exception {
        // Given
        long carId = 1L;

        doNothing().when(_service).deleteById(carId);

        // When
        mockMvc.perform(delete("/companies/{id}", carId))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDeleteNotExistingCarThenRespondWithNotFound() throws Exception {
        // given
        long carId = 1L;

        doThrow(new CarNotFoundException()).when(_service).deleteById(carId);

        // When
        mockMvc.perform(delete("/api/cars/{id}", carId))
                .andExpect(status().isNotFound());
    }

}
