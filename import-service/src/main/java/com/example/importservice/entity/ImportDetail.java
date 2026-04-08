package com.example.importservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "import_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportDetail {

    @Id
    private String detailId;

    private String importTicketId;

    private String productId;

    private int quantity;

    private double unitPrice;

    private double subTotal;
}
