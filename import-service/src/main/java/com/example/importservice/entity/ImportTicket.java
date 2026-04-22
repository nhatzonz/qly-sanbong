package com.example.importservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "import_tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportTicket {

    @Id
    private String importTicketId;

    private Long staffId;

    private String supplierId;

    private String date;

    private double amount;

    private String createdBy;

    private String note;
}
