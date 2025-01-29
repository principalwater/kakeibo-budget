package com.kakeibo.bills.service;

import com.kakeibo.bills.model.BillMetadata;
import com.kakeibo.bills.repository.BillRepository;
import org.springframework.stereotype.Service;

@Service
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public void saveBillMetadata(BillMetadata metadata) {
        billRepository.save(metadata);
    }
}