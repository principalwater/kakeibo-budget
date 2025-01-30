package com.kakeibo.bills.repository;

import com.kakeibo.bills.model.BillMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillMetadataRepository extends JpaRepository<BillMetadata, Long> {

    /**
     * Finds a bill by its file name.
     *
     * @param fileName The name of the file.
     * @return Optional containing BillMetadata if found.
     */
    Optional<BillMetadata> findByFileName(String fileName);

    /**
     * Checks whether a file already exists in the database.
     *
     * @param fileName The file name to check.
     * @return true if the file exists, false otherwise.
     */
    boolean existsByFileName(String fileName);
}