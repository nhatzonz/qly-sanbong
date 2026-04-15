package com.example.importservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportDetailDTO {

    @NotBlank(message = "Ma san pham khong duoc de trong")
    private String productId;

    private String productName;

    @Min(value = 1, message = "So luong phai lon hon 0")
    private int quantity;

    @Min(value = 0, message = "Don gia khong duoc am")
    private double unitPrice;
}
