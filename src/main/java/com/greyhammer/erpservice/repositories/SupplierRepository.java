package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.Supplier;
import org.springframework.data.repository.CrudRepository;


public interface SupplierRepository extends CrudRepository<Supplier, Long> {
}
