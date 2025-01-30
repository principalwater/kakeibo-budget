package com.kakeibo.bills.service;

import com.kakeibo.bills.model.BillMetadata;
import com.kakeibo.bills.repository.BillMetadataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillMetadataService {
    private final BillMetadataRepository billMetadataRepository;

    public BillMetadataService(BillMetadataRepository billMetadataRepository) {
        this.billMetadataRepository = billMetadataRepository;
    }

    /**
     * Retrieves all stored bill metadata.
     *
     * @return List of all BillMetadata.
     */
    public List<BillMetadata> getAllBillMetadata() {
        return billMetadataRepository.findAll();
    }

    /**
     * Retrieves metadata for a specific bill file by filename.
     *
     * @param fileName The name of the bill file.
     * @return Optional containing BillMetadata if found, otherwise empty.
     */
    public Optional<BillMetadata> getBillMetadataByFileName(String fileName) {
        return billMetadataRepository.findByFileName(fileName);
    }

    /**
     * Checks if a bill file is already recorded in the database.
     *
     * @param fileName The name of the bill file.
     * @return true if the file exists, false otherwise.
     */
    public boolean existsByFileName(String fileName) {
        return billMetadataRepository.existsByFileName(fileName);
    }

    /**
     * Saves bill metadata **only if it does not already exist** in the database.
     *
     * @param metadata Bill metadata object.
     * @return true if the metadata was saved, false if it already existed.
     */
    public boolean saveBillMetadataIfNotExists(BillMetadata metadata) {
        if (billMetadataRepository.findByFileName(metadata.getFileName()).isEmpty()) {
            billMetadataRepository.save(metadata);
            return true; // Metadata was newly inserted
        }
        return false; // Metadata already exists
    }
}