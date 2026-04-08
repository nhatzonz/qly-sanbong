package com.example.importservice.service;

import com.example.importservice.dto.ImportDetailDTO;
import com.example.importservice.dto.ImportRequestDTO;
import com.example.importservice.entity.ImportDetail;
import com.example.importservice.entity.ImportTicket;
import com.example.importservice.repository.ImportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ImportService {

    private final ImportRepository importRepository;

    public ImportService(ImportRepository importRepository) {
        this.importRepository = importRepository;
    }

    public ImportTicket createImport(ImportRequestDTO dto) {
        List<ImportDetail> details = new ArrayList<>();
        float totalAmount = 0;

        for (ImportDetailDTO d : dto.getDetails()) {
            double subTotal = d.getQuantity() * d.getUnitPrice();
            totalAmount += subTotal;

            details.add(ImportDetail.builder()
                    .detailId(UUID.randomUUID().toString())
                    .importTicketId(dto.getImportTicketId())
                    .productId(d.getProductId())
                    .quantity(d.getQuantity())
                    .unitPrice(d.getUnitPrice())
                    .subTotal(subTotal)
                    .build());
        }

        ImportTicket ticket = ImportTicket.builder()
                .importTicketId(dto.getImportTicketId())
                .supplierId(dto.getSupplierId())
                .date(dto.getDate())
                .amount(totalAmount)
                .createdBy(dto.getCreatedBy())
                .note(dto.getNote())
                .details(details)
                .build();

        return importRepository.save(ticket);
    }
}
