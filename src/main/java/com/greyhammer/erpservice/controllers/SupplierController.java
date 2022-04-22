package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.models.Supplier;
import com.greyhammer.erpservice.services.SupplierService;
import com.greyhammer.erpservice.views.PurchaseOrderView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @JsonView({PurchaseOrderView.FullView.class})
    @RequestMapping("/api/suppliers")
    public ResponseEntity<Set<Supplier>> getAll() {
        return ResponseEntity.ok(supplierService.getAll());
    }
}
