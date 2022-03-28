package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
