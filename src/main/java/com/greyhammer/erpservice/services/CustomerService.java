package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.exceptions.CustomerNotFoundException;
import com.greyhammer.erpservice.models.Customer;

import java.util.Optional;
import java.util.Set;

public interface CustomerService {
    Customer getOrCreate(Customer customer) throws CustomerNotFoundException;
    Customer save(Customer customer);
    Customer getById(Long id) throws CustomerNotFoundException;
    Set<Customer> getAll();
}
