package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.exceptions.CustomerNotFoundException;
import com.greyhammer.erpservice.models.Customer;
import com.greyhammer.erpservice.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            Optional<Customer> existing = getById(customerId);

            if (existing.isEmpty())
                throw new CustomerNotFoundException();
            else {
                return existing.get();
            }
        }
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getById(Long id) {
        return customerRepository.findById(id);
    }
}
