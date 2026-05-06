package com.example.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "Ten dang nhap khong duoc de trong")
    private String userName;

    @NotBlank(message = "Mat khau khong duoc de trong")
    private String passWord;

    @NotBlank(message = "Xac nhan mat khau khong duoc de trong")
    private String confirmPassword;
}
