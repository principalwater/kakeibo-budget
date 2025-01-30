package com.kakeibo.bills.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "bills_metadata")
public class BillMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String counterparty;

    @Column(nullable = false)
    private LocalDate period;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(name = "filename", nullable = false, unique = true)
    private String fileName;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public BillMetadata() {
        this.createdAt = LocalDateTime.now();
    }

    public BillMetadata(String type, String counterparty, String period, BigDecimal amount, String currency, String fileName) {
        this.type = type.toLowerCase();
        this.counterparty = counterparty;
        this.amount = amount;
        this.currency = currency;
        this.fileName = fileName;
        this.createdAt = LocalDateTime.now();

        if (period.length() == 7) {
            this.period = LocalDate.parse(period + "-01", DateTimeFormatter.ISO_DATE);
        } else {
            this.period = LocalDate.parse(period, DateTimeFormatter.ISO_DATE);
        }
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public LocalDate getPeriod() {
        return period;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getFileName() {
        return fileName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setType(String type) {
        this.type = type.toLowerCase();
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public void setPeriod(LocalDate period) {
        this.period = period;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}