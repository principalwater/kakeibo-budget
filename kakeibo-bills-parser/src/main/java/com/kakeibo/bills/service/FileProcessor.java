package com.kakeibo.bills.service;

import com.kakeibo.bills.model.BillMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileProcessor {

    private static final Logger log = LoggerFactory.getLogger(FileProcessor.class);

    private final BillService billService;
    private final MinIOService minIOService;
    private final String watchDirectory;
    private final boolean deleteAfterProcessing;

    public FileProcessor(BillService billService, MinIOService minIOService, String watchDirectory) {
        this(billService, minIOService, watchDirectory, true); // Default behavior: delete files
    }

    public FileProcessor(BillService billService, MinIOService minIOService, String watchDirectory, boolean deleteAfterProcessing) {
        this.billService = billService;
        this.minIOService = minIOService;
        this.watchDirectory = watchDirectory;
        this.deleteAfterProcessing = deleteAfterProcessing;
    }

    @Scheduled(fixedRate = 86400000) // Run once a day (every 24 hours)
    public void processFiles() {
        try {
            Path directoryPath = Paths.get(watchDirectory);
            if (!Files.exists(directoryPath)) {
                log.warn("Directory {} does not exist.", watchDirectory);
                return;
            }

            Files.list(directoryPath)
                    .filter(path -> path.toString().endsWith(".pdf"))
                    .forEach(this::processFile);

        } catch (Exception e) {
            log.error("Error while processing bills: ", e);
        }
    }

    private void processFile(Path filePath) {
        try {
            File file = filePath.toFile();
            String fileName = file.getName();

            if (!fileName.startsWith("Bill_")) {
                log.warn("Skipping file {} - does not match bill naming pattern.", fileName);
                return;
            }

            // Parse metadata from file name
            String[] parts = fileName.replace("Bill_", "").replace(".pdf", "").split("_");
            if (parts.length != 4) {
                log.warn("Skipping file {} - incorrect format.", fileName);
                return;
            }

            String counterparty = parts[0];
            String period = parts[1].substring(0, 7); // Extract year and month
            // TODO: change to standard data type like decimal
            BigDecimal amount = new BigDecimal(parts[2].replace(",", ".")); // Ensure correct decimal parsing
            String currency = parts[3];

            BillMetadata billMetadata = new BillMetadata(counterparty, period, amount, currency, fileName);

            // Save metadata and upload the file
            billService.saveBillMetadata(billMetadata);

            try (InputStream inputStream = Files.newInputStream(filePath)) {
                minIOService.uploadFile(fileName, inputStream, file.length());
            }

            // Only delete file if deleteAfterProcessing is true
            if (deleteAfterProcessing) {
                Files.delete(filePath);
                log.info("Processed and removed file: {}", fileName);
            } else {
                log.info("Processed file but NOT removed (test mode): {}", fileName);
            }

        } catch (Exception e) {
            log.error("Error processing file: {}", filePath, e);
        }
    }
}