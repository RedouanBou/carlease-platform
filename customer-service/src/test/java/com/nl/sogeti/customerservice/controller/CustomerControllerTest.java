package com.nl.sogeti.customerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.sogeti.customerservice.TestConfig;
import com.nl.sogeti.customerservice.dto.CustomerRequestDto;
import com.nl.sogeti.customerservice.entities.Customer;
import com.nl.sogeti.customerservice.exception.CustomerNotFoundException;
import com.nl.sogeti.customerservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.get;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService _service;

    @Test
    void whenGetCustomersThenReturnListOfCustomers() throws Exception {
        // Given
        var customers = Arrays.asList(
                createCustomerWithId(1L),
                createCustomerWithId(2L)
        );

        when(_service.getAll()).thenReturn(customers);

        // When
        mockMvc.perform(get("/api/customers")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(customers.size()))
                .andExpect(jsonPath("*.id").isNotEmpty());
    }

    private static Customer createCustomerWithId(long id) {
        return Customer.builder()
                .id(id)
                .name("name")
                .street("street")
                .houseNumber(12)
                .zipCode("1234AA")
                .place("Place")
                .emailAddress("test@test.com")
                .phoneNumber("0612345678")
                .build();
    }

    @Test
    void whenNoCustomersThenGetCustomersReturnEmptyList() throws Exception {
        // Given
        when(_service.getAll()).thenReturn(Collections.emptyList());

        // When
        mockMvc.perform(get("/api/customers")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenCustomerIdExistsThenGetCustomerRespondsWithOk() throws Exception {
        // Given
        long customerId = 1L;

        // When
        when(_service.getById(customerId)).thenReturn(new Customer());

        mockMvc.perform(get("/api/customers/{id}", customerId)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenCustomerDoesNotExistThenGetCustomerRespondsWithNotFound() throws Exception {
        // Given
        long customerId = 10L;
        when(_service.getById(customerId)).thenThrow(new CustomerNotFoundException());

        // When
        mockMvc.perform(get("/api/customers/{id}", customerId)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenCreateCustomerThenRespondWithOk() throws Exception {
        // Given
        var customer = CustomerRequestDto.builder()
                .name("name")
                .street("street")
                .houseNumber(12)
                .zipCode("1234AA")
                .place("Place")
                .emailAddress("test@test.com")
                .phoneNumber("0612345678")
                .build();

        when(_service.create(any())).thenReturn(new Customer());

        // When
        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());
    }

    @Test
    void whenCreateCustomerInvalidThenRespondWithBadRequest() throws Exception {
        // When
        mockMvc.perform(post("/api/customers")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUpdateCustomerThenRespondWithOk() throws Exception {
        // Given
        var customer = CustomerRequestDto.builder()
                .name("name")
                .street("street")
                .houseNumber(12)
                .zipCode("1234AA")
                .place("Place")
                .emailAddress("test@test.com")
                .phoneNumber("0612345678")
                .build();

        var mockedCustomer = new Customer();
        when(_service.update(any())).thenReturn(mockedCustomer);

        // When
        mockMvc.perform(put("/api/customers/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());
    }

    @Test
    void whenUpdateCustomerInvalidThenRespondWithBadRequest() throws Exception {
        when(_service.create(any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not all fields are specified"));

        // When
        mockMvc.perform(put("/api/customers/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCustomerDoesNotExistThenUpdateCustomerRespondsWithNotFound() throws Exception {
        // given
        var customer = CustomerRequestDto.builder()
                .name("name")
                .street("street")
                .houseNumber(12)
                .zipCode("1234AA")
                .place("Place")
                .emailAddress("test@test.com")
                .phoneNumber("0612345678")
                .build();

        when(_service.update(any())).thenThrow(new CustomerNotFoundException());

        // When
        mockMvc.perform(put("/api/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenDeleteCustomerThenRespondWithNoContent() throws Exception {
        // Given
        long customerId = 1L;
        doNothing().when(_service).deleteById(customerId);

        // When
        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDeleteNotExistingCustomerThenRespondWithNotFound() throws Exception {
        // given
        long customerId = 1L;

        doThrow(new CustomerNotFoundException()).when(_service).deleteById(customerId);

        // When
        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isNotFound());
    }

}
