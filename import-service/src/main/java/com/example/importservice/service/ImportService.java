package com.example.importservice.service;

import com.example.importservice.dto.ImportDetailDTO;
import com.example.importservice.dto.ImportRequestDTO;
import com.example.importservice.dto.StaffDTO;
import com.example.importservice.entity.ImportDetail;
import com.example.importservice.entity.ImportTicket;
import com.example.importservice.repository.ImportDetailRepository;
import com.example.importservice.repository.ImportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class ImportService {

    private final ImportRepository importRepository;
    private final ImportDetailRepository importDetailRepository;
    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public ImportService(ImportRepository importRepository, ImportDetailRepository importDetailRepository, RestTemplate restTemplate) {
        this.importRepository = importRepository;
        this.importDetailRepository = importDetailRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public ImportTicket createImport(ImportRequestDTO dto) {
        log.info("[NHAP HANG] Bat dau tao phieu - Ma phieu: {}, NCC: {}, Ngay: {}, NV ID: {}",
                dto.getImportTicketId(), dto.getSupplierId(), dto.getDate(), dto.getStaffId());

        // Kiem tra trung ma phieu nhap
        if (importRepository.existsById(dto.getImportTicketId())) {
            throw new IllegalArgumentException("Mã phiếu nhập '" + dto.getImportTicketId() + "' đã tồn tại");
        }

        // Lay thong tin nhan vien tu UserService
        StaffDTO staff;
        try {
            staff = restTemplate.getForObject(userServiceUrl + "/api/staff/" + dto.getStaffId(), StaffDTO.class);
            if (staff == null) throw new IllegalArgumentException("Khong tim thay nhan vien");
            log.info("[NHAP HANG] Nhan vien: {}, Vi tri: {}", staff.getName(), staff.getPosition());
        } catch (Exception e) {
            log.error("[NHAP HANG] Loi khi lay thong tin nhan vien ID {}: {}", dto.getStaffId(), e.getMessage());
            throw new IllegalArgumentException("Không tìm thấy nhân viên ID: " + dto.getStaffId());
        }

        List<ImportDetail> details = new ArrayList<>();
        double totalAmount = 0;

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

            syncProductStock(d);
        }

        ImportTicket ticket = ImportTicket.builder()
                .importTicketId(dto.getImportTicketId())
                .staffId(staff.getUserId())
                .supplierId(dto.getSupplierId())
                .date(dto.getDate())
                .amount(totalAmount)
                .createdBy(staff.getName())
                .note(dto.getNote())
                .build();

        importRepository.save(ticket);
        importDetailRepository.saveAll(details);

        log.info("[NHAP HANG] Thanh cong - Ma phieu: {}, Tong tien: {}, So san pham: {}, Nhan vien: {}",
                ticket.getImportTicketId(), ticket.getAmount(), details.size(), ticket.getCreatedBy());
        return ticket;
    }

    private void syncProductStock(ImportDetailDTO d) {
        try {
            String existsUrl = productServiceUrl + "/api/products/" + d.getProductId() + "/exists";
            Boolean exists = restTemplate.getForObject(existsUrl, Boolean.class);

            if (Boolean.TRUE.equals(exists)) {
                String updateUrl = productServiceUrl + "/api/products/" + d.getProductId() + "/stock?quantity=" + d.getQuantity();
                restTemplate.put(updateUrl, null);
                log.info("[TON KHO] Cap nhat san pham: {}, them: {}", d.getProductId(), d.getQuantity());
            } else {
                Map<String, Object> newProduct = new HashMap<>();
                newProduct.put("productId", d.getProductId());
                newProduct.put("productName", d.getProductName() != null ? d.getProductName() : d.getProductId());
                newProduct.put("price", d.getUnitPrice());
                newProduct.put("unit", d.getQuantity());
                restTemplate.postForObject(productServiceUrl + "/api/products", newProduct, Object.class);
                log.info("[TON KHO] Tao moi san pham: {}, so luong: {}", d.getProductId(), d.getQuantity());
            }
        } catch (Exception e) {
            log.error("[TON KHO] Loi khi cap nhat san pham {}: {}", d.getProductId(), e.getMessage());
        }
    }
}
