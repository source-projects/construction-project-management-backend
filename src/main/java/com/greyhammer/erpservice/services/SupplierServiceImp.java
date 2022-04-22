package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.exceptions.CustomerNotFoundException;
import com.greyhammer.erpservice.exceptions.SupplierNotFoundException;
import com.greyhammer.erpservice.models.Customer;
import com.greyhammer.erpservice.models.Supplier;
import com.greyhammer.erpservice.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SupplierServiceImp implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImp(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier getOrCreate(Supplier supplier) throws SupplierNotFoundException {
        Long supplierId = supplier.getId();

        if (supplierId == null) {
            return save(supplier);
        } else {
            return get(supplierId);
        }
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier get(Long id) throws SupplierNotFoundException {
        Optional<Supplier> supplier = supplierRepository.findById(id);

        if (supplier.isEmpty())
            throw new SupplierNotFoundException();

        return supplier.get();
    }

    @Override
    public Set<Supplier> getAll() {
        Set<Supplier> suppliers = new HashSet<>();
        supplierRepository.findAll().iterator().forEachRemaining(suppliers::add);
        return suppliers;
    }
}
