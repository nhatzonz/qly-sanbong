package com.example.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    @NotBlank(message = "Ma khach hang khong duoc de trong")
    @Size(max = 20, message = "Ma khach hang toi da 20 ky tu")
    private String idCustomer;

    @NotBlank(message = "Ho ten khong duoc de trong")
    @Size(min = 2, max = 100, message = "Ho ten phai tu 2 den 100 ky tu")
    private String name;

    @NotBlank(message = "So dien thoai khong duoc de trong")
    @Pattern(regexp = "^(0[3|5|7|8|9])+([0-9]{8})$", message = "So dien thoai khong hop le (VD: 0901234567)")
    private String phone;

    @NotBlank(message = "Dia chi khong duoc de trong")
    private String address;
}
