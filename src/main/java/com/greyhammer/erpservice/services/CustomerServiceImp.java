package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.exceptions.CustomerNotFoundException;
import com.greyhammer.erpservice.models.Customer;
import com.greyhammer.erpservice.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerServiceImp implements CustomerService {
    CustomerRepository customerRepository;

    public CustomerServiceImp(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getOrCreate(Customer customer) throws CustomerNotFoundException {
        Long customerId = customer.getId();

        if (customerId == null) {
            return save(customer);
        } else {
            return getById(customerId);
        }
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getById(Long id) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty())
            throw new CustomerNotFoundException();

        return customer.get();
    }

    @Override
    public Set<Customer> getAll() {
        Set<Customer> customers = new HashSet<>();
        customerRepository.findAll().iterator().forEachRemaining(customers::add);
        return customers;
    }
}
