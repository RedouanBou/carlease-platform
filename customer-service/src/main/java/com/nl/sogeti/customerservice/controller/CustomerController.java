package com.nl.sogeti.customerservice.controller;

import com.nl.sogeti.customerservice.dto.CustomerRequestDto;
import com.nl.sogeti.customerservice.dto.CustomerResponseDto;
import com.nl.sogeti.customerservice.entities.Customer;
import com.nl.sogeti.customerservice.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService _service;
    private final ModelMapper _mapper;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getCustomers() {
        List<Customer> customers = this._service.getAll();

        List<CustomerResponseDto> response = customers.stream()
                .map(this::mapCustomer)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    private CustomerResponseDto mapCustomer(Customer customer) {
        return CustomerResponseDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .street(customer.getStreet())
                .houseNumber(customer.getHouseNumber())
                .zipCode(customer.getZipCode())
                .place(customer.getPlace())
                .emailAddress(customer.getEmailAddress())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable long id) {
        var customer = this._service.getById(id);

        var response = this._mapper.map(customer, CustomerResponseDto.class);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody @Valid CustomerRequestDto customerDto) {
        var mappedCustomer = this._mapper.map(customerDto, Customer.class);

        var createdCustomer = this._service.create(mappedCustomer);

        var response = this._mapper.map(createdCustomer, CustomerResponseDto.class);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable long id, @RequestBody @Valid CustomerRequestDto customerDto) {
        var mappedCustomer = this._mapper.map(customerDto, Customer.class);
        mappedCustomer.setId(id);

        var updatedCustomer = this._service.update(mappedCustomer);

        var response = this._mapper.map(updatedCustomer, CustomerResponseDto.class);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
        this._service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
