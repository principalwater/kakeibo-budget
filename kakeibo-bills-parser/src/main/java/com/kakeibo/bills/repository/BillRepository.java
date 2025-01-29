package com.kakeibo.bills.repository;

import com.kakeibo.bills.model.BillMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<BillMetadata, Long> {
}