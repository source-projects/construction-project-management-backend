package com.greyhammer.erpservice.commands;

import com.greyhammer.erpservice.models.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class CreateProjectCommand {
    private String name;
    private String description;

    private Customer customer;
    private Boolean hasExistingDesign;
}
