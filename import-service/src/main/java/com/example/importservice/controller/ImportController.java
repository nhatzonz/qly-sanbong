package com.example.importservice.controller;

import com.example.importservice.dto.ImportRequestDTO;
import com.example.importservice.entity.ImportTicket;
import com.example.importservice.service.ImportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/imports")
public class ImportController {

    private final ImportService importService;

    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping
    public ResponseEntity<ImportTicket> createImport(@Valid @RequestBody ImportRequestDTO dto) {
        ImportTicket ticket = importService.createImport(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }
}
