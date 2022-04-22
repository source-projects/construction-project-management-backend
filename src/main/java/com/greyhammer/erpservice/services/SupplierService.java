package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.exceptions.SupplierNotFoundException;
import com.greyhammer.erpservice.models.Supplier;

import java.util.Set;

public interface SupplierService {
    Supplier getOrCreate(Supplier supplier) throws SupplierNotFoundException;
    Supplier save(Supplier supplier);
    Supplier get(Long id) throws SupplierNotFoundException;
    Set<Supplier> getAll();
}
