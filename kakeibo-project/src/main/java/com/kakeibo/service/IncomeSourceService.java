package com.kakeibo.service;

import com.kakeibo.model.IncomeSource;
import com.kakeibo.repository.IncomeSourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeSourceService {

    private final IncomeSourceRepository incomeSourceRepository;

    public IncomeSourceService(IncomeSourceRepository incomeSourceRepository) {
        this.incomeSourceRepository = incomeSourceRepository;
    }

    public List<IncomeSource> getAllIncomeSources() {
        return incomeSourceRepository.findAll();
    }

    public Optional<IncomeSource> getIncomeSourceById(Long id) {
        return incomeSourceRepository.findById(id);
    }

    public IncomeSource saveIncomeSource(IncomeSource incomeSource) {
        return incomeSourceRepository.save(incomeSource);
    }

    public void deleteIncomeSource(Long id) {
        incomeSourceRepository.deleteById(id);
    }

    public Optional<IncomeSource> getIncomeSourceByName(String name) {
        return Optional.ofNullable(incomeSourceRepository.findByName(name));
    }
}