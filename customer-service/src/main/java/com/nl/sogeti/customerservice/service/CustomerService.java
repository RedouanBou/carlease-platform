package com.nl.sogeti.customerservice.service;

import com.nl.sogeti.customerservice.entities.Customer;
import com.nl.sogeti.customerservice.exception.CustomerNotFoundException;
import com.nl.sogeti.customerservice.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository _repository;

    public List<Customer> getAll() {
        return this._repository.findAll();
    }

    public Customer getById(long id) {
        return this._repository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public Customer create(Customer customer) {
        return this._repository.save(customer);
    }

    public Customer update(Customer customer) {
        var existingCustomer = this._repository.findById(customer.getId())
                .orElseThrow(CustomerNotFoundException::new);

        var updatedCustomer = Customer.builder()
                .id(existingCustomer.getId())
                .name(customer.getName())
                .street(customer.getStreet())
                .houseNumber(customer.getHouseNumber())
                .zipCode(customer.getZipCode())
                .place(customer.getPlace())
                .emailAddress(customer.getEmailAddress())
                .phoneNumber(customer.getPhoneNumber())
                .build();

        return this._repository.save(updatedCustomer);
    }

    public void deleteById(long id) {
        var customer = this._repository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        this._repository.delete(customer);
    }

}
