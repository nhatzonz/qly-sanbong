package com.example.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank(message = "Ten dang nhap khong duoc de trong")
    private String userName;

    @NotBlank(message = "Mat khau khong duoc de trong")
    private String passWord;
}
