package com.example.userservice.config;

import com.example.userservice.entity.Staff;
import com.example.userservice.entity.User;
import com.example.userservice.repository.StaffRepository;
import com.example.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    public DataInitializer(UserRepository userRepository, StaffRepository staffRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() == 0) {
            User user = userRepository.save(User.builder()
                    .userName("nhatzonz")
                    .passWord("123")
                    .build());

            staffRepository.save(Staff.builder()
                    .userId(user.getId())
                    .name("Nguyễn Văn Nhất")
                    .phone("0387556219")
                    .position("Nhân viên")
                    .address("")
                    .salary(0)
                    .build());

            log.info("[INIT] Da tao tai khoan: nhatzonz | Nhan vien: Nguyen Van Nhat | ID: {}", user.getId());
        }
    }
}
