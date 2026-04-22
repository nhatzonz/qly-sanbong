package com.example.userservice.service;

import com.example.userservice.dto.LoginRequestDTO;
import com.example.userservice.dto.LoginResponseDTO;
import com.example.userservice.entity.Staff;
import com.example.userservice.repository.StaffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final StaffRepository staffRepository;

    public AuthService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        log.info("[DANG NHAP] Tai khoan: {}", dto.getUserName());

        Staff staff = staffRepository.findByUserNameAndPassWord(dto.getUserName(), dto.getPassWord())
                .orElseThrow(() -> new IllegalArgumentException("Tai khoan hoac mat khau khong dung"));

        log.info("[DANG NHAP] Thanh cong - Nhan vien: {}, ID: {}", staff.getName(), staff.getId());

        return LoginResponseDTO.builder()
                .staffId(staff.getId())
                .name(staff.getName())
                .phone(staff.getPhone())
                .position(staff.getPosition())
                .build();
    }

    public Staff getStaffById(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay nhan vien ID: " + id));
    }
}
