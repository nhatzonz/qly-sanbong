package com.example.importservice.service;

import com.example.importservice.dto.ImportDetailDTO;
import com.example.importservice.dto.ImportRequestDTO;
import com.example.importservice.entity.ImportDetail;
import com.example.importservice.entity.ImportTicket;
import com.example.importservice.repository.ImportDetailRepository;
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
    private final ImportDetailRepository importDetailRepository;

    public ImportService(ImportRepository importRepository, ImportDetailRepository importDetailRepository) {
        this.importRepository = importRepository;
        this.importDetailRepository = importDetailRepository;
    }

    public ImportTicket createImport(ImportRequestDTO dto) {
        log.info("[NHAP HANG] Bat dau tao phieu - Ma phieu: {}, NCC: {}, Ngay: {}, Nguoi tao: {}",
                dto.getImportTicketId(), dto.getSupplierId(), dto.getDate(), dto.getCreatedBy());

        List<ImportDetail> details = new ArrayList<>();
        float totalAmount = 0;

        for (ImportDetailDTO d : dto.getDetails()) {
            double subTotal = d.getQuantity() * d.getUnitPrice();
            totalAmount += subTotal;
            log.info("[NHAP HANG] Chi tiet - San pham: {}, So luong: {}, Don gia: {}, Thanh tien: {}",
                    d.getProductId(), d.getQuantity(), d.getUnitPrice(), subTotal);

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
                .build();

        importRepository.save(ticket);
        importDetailRepository.saveAll(details);

        log.info("[NHAP HANG] Thanh cong - Ma phieu: {}, Tong tien: {}, So san pham: {}",
                ticket.getImportTicketId(), ticket.getAmount(), details.size());
        return ticket;
    }
}
