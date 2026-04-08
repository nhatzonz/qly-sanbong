package com.example.supplierservice.service;

import com.example.supplierservice.dto.SupplierDTO;
import com.example.supplierservice.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierDTO> getAll() {
        return supplierRepository.findAll().stream()
                .map(s -> new SupplierDTO(s.getSupplierId(), s.getName(), s.getPhone(), s.getAddress()))
                .collect(Collectors.toList());
    }
}
