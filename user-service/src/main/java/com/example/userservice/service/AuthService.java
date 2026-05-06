package com.example.userservice.service;

import com.example.userservice.dto.LoginRequestDTO;
import com.example.userservice.dto.LoginResponseDTO;
import com.example.userservice.dto.RegisterRequestDTO;
import com.example.userservice.entity.Staff;
import com.example.userservice.repository.StaffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final StaffRepository staffRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        log.info("[DANG NHAP] Tai khoan: {}", dto.getUserName());

        Staff staff = staffRepository.findByUserName(dto.getUserName())
                .filter(s -> passwordEncoder.matches(dto.getPassWord(), s.getPassWord()))
                .orElseThrow(() -> new IllegalArgumentException("Tai khoan hoac mat khau khong dung"));

        log.info("[DANG NHAP] Thanh cong - Nhan vien: {}, ID: {}", staff.getName(), staff.getId());

        return LoginResponseDTO.builder()
                .staffId(staff.getId())
                .name(staff.getName())
                .phone(staff.getPhone())
                .position(staff.getPosition())
                .build();
    }

    public LoginResponseDTO register(RegisterRequestDTO dto) {
        log.info("[DANG KY] Tai khoan: {}", dto.getUserName());

        if (!dto.getPassWord().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Mat khau xac nhan khong khop");
        }

        if (staffRepository.existsByUserName(dto.getUserName())) {
            throw new IllegalArgumentException("Ten dang nhap da ton tai");
        }

        Staff staff = Staff.builder()
                .userName(dto.getUserName())
                .passWord(passwordEncoder.encode(dto.getPassWord()))
                .build();

        Staff saved = staffRepository.save(staff);
        if (saved.getName() == null || saved.getName().isBlank()) {
            saved.setName("Nhân viên " + saved.getId());
            saved = staffRepository.save(saved);
        }
        log.info("[DANG KY] Thanh cong - Nhan vien: {}, ID: {}", saved.getName(), saved.getId());

        return LoginResponseDTO.builder()
                .staffId(saved.getId())
                .name(saved.getName())
                .phone(saved.getPhone())
                .position(saved.getPosition())
                .build();
    }

    public Staff getStaffById(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay nhan vien ID: " + id));
    }
}
