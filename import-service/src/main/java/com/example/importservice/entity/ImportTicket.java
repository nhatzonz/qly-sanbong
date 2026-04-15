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

    private String supplierId;

    private String date;

    private float amount;

    private String createdBy;

    private String note;
}
