package com.example.supplierservice.service;

import com.example.supplierservice.dto.SupplierDTO;
import com.example.supplierservice.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierDTO> getAll() {
        List<SupplierDTO> list = supplierRepository.findAll().stream()
                .map(s -> new SupplierDTO(s.getSupplierId(), s.getName(), s.getPhone(), s.getAddress()))
                .collect(Collectors.toList());
        log.info("[LAY NHA CUNG CAP] Tra ve {} nha cung cap", list.size());
        return list;
    }
}
