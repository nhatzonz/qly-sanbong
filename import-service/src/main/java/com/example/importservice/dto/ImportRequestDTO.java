package com.example.importservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportRequestDTO {
    private String importTicketId;
    private String date;
    private String supplierId;
    private String createdBy;
    private String note;
    private List<ImportDetailDTO> details;
}
