package com.example.userservice.controller;

import com.example.userservice.dto.CustomerRequestDTO;
import com.example.userservice.entity.Customer;
import com.example.userservice.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<java.util.List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerRequestDTO dto) {
        Customer customer = customerService.createCustomer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
}
