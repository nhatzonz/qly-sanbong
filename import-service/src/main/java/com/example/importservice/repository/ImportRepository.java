package com.example.importservice.repository;

import com.example.importservice.entity.ImportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportRepository extends JpaRepository<ImportTicket, String> {
}
