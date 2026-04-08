package com.example.importservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportDetailDTO {
    private String productId;
    private String productName;
    private int quantity;
    private double unitPrice;
}
