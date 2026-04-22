package com.example.userservice.config;

import com.example.userservice.entity.Staff;
import com.example.userservice.repository.StaffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer implements ApplicationRunner {

    private final StaffRepository staffRepository;

    public DataInitializer(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (staffRepository.count() == 0) {
            Staff staff = Staff.builder()
                    .userName("nhatzonz")
                    .passWord("123")
                    .name("Nguyễn Văn Nhất")
                    .phone("0387556219")
                    .position("Nhân viên")
                    .address("")
                    .salary(0)
                    .build();

            staffRepository.save(staff);
            log.info("[INIT] Da tao tai khoan: nhatzonz | Nhan vien: Nguyen Van Nhat | ID: {}", staff.getId());
        }
    }
}
