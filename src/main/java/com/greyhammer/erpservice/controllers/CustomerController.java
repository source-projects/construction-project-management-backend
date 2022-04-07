package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.models.Customer;
import com.greyhammer.erpservice.services.CustomerService;
import com.greyhammer.erpservice.views.ProjectView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @JsonView({ProjectView.FullView.class})
    @RequestMapping("/api/customers")
    public ResponseEntity<Set<Customer>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }
}
