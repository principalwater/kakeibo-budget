package com.kakeibo.repository;

import com.kakeibo.model.IncomeSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeSourceRepository extends JpaRepository<IncomeSource, Long> {
    // Custom query to find an income source by name
    IncomeSource findByName(String name);
}