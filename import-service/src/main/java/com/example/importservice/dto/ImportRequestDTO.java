package com.example.importservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportRequestDTO {

    @NotBlank(message = "Ma phieu nhap khong duoc de trong")
    private String importTicketId;

    @NotBlank(message = "Ngay nhap khong duoc de trong")
    private String date;

    @NotBlank(message = "Ma nha cung cap khong duoc de trong")
    private String supplierId;

    @NotBlank(message = "Nguoi tao khong duoc de trong")
    private String createdBy;

    private String note;

    @NotEmpty(message = "Phieu nhap phai co it nhat 1 san pham")
    @Valid
    private List<ImportDetailDTO> details;
}
