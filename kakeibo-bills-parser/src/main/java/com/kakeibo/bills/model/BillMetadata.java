package com.kakeibo.bills.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
public class BillMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String counterparty;

    @Column(nullable = false)
    private LocalDate period;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 10)
    private String currency;

    @Column(nullable = false, unique = true)
    private String fileName;

    public BillMetadata() {}

    public BillMetadata(String counterparty, String period, BigDecimal amount, String currency, String fileName) {
        this.counterparty = counterparty;
        this.period = LocalDate.parse(period.substring(0, 7) + "-01");
        this.amount = amount;
        this.currency = currency;
        this.fileName = fileName;
    }
}