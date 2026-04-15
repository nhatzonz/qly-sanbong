package com.example.importservice.repository;

import com.example.importservice.entity.ImportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportDetailRepository extends JpaRepository<ImportDetail, String> {
}
