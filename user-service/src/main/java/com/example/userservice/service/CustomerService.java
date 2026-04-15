package com.example.userservice.service;

import com.example.userservice.dto.CustomerRequestDTO;
import com.example.userservice.entity.Customer;
import com.example.userservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerRequestDTO dto) {
        log.info("[THEM KHACH HANG] Ma: {}, Ten: {}, SDT: {}, Dia chi: {}",
                dto.getIdCustomer(), dto.getName(), dto.getPhone(), dto.getAddress());

        if (customerRepository.existsById(dto.getIdCustomer())) {
            log.warn("[THEM KHACH HANG] Ma {} da ton tai", dto.getIdCustomer());
            throw new IllegalArgumentException("Mã khách hàng '" + dto.getIdCustomer() + "' đã tồn tại");
        }

        Customer customer = Customer.builder()
                .customerId(dto.getIdCustomer())
                .name(dto.getName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .build();

        Customer saved = customerRepository.save(customer);
        log.info("[THEM KHACH HANG] Thanh cong - Ma khach hang: {}", saved.getCustomerId());
        return saved;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
