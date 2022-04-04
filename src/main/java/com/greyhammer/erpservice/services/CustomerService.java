package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.exceptions.CustomerNotFoundException;
import com.greyhammer.erpservice.models.Customer;

import java.util.Optional;

public interface CustomerService {
    Customer getOrCreate(Customer customer) throws CustomerNotFoundException;
    Customer save(Customer customer);
    Optional<Customer> getById(Long id);
}
