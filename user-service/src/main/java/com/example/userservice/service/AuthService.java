package com.example.userservice.service;

import com.example.userservice.dto.LoginRequestDTO;
import com.example.userservice.dto.LoginResponseDTO;
import com.example.userservice.entity.Staff;
import com.example.userservice.entity.User;
import com.example.userservice.repository.StaffRepository;
import com.example.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    public AuthService(UserRepository userRepository, StaffRepository staffRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        log.info("[DANG NHAP] Tai khoan: {}", dto.getUserName());

        User user = userRepository.findByUserNameAndPassWord(dto.getUserName(), dto.getPassWord())
                .orElseThrow(() -> new IllegalArgumentException("Tai khoan hoac mat khau khong dung"));

        Staff staff = staffRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay thong tin nhan vien"));

        log.info("[DANG NHAP] Thanh cong - Nhan vien: {}, ID: {}", staff.getName(), staff.getUserId());

        return LoginResponseDTO.builder()
                .staffId(staff.getUserId())
                .name(staff.getName())
                .phone(staff.getPhone())
                .position(staff.getPosition())
                .build();
    }
}
