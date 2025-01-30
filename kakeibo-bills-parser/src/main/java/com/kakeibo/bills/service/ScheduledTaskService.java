package com.kakeibo.bills.service;

import com.kakeibo.bills.config.SchedulerConfig;
import com.kakeibo.bills.model.BillMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ScheduledTaskService {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTaskService.class);

    private final MinIOService minIOService;
    private final BillMetadataService billMetadataService;
    private final SchedulerConfig schedulerConfig;

    public ScheduledTaskService(MinIOService minIOService, BillMetadataService billMetadataService, SchedulerConfig schedulerConfig) {
        this.minIOService = minIOService;
        this.billMetadataService = billMetadataService;
        this.schedulerConfig = schedulerConfig;
    }

    @Scheduled(cron = "#{schedulerConfig.getCron()}", zone = "Europe/Moscow")
    public void processNewFiles() {
        log.info("Scheduled task triggered: Checking MinIO for new files...");

        try {
            List<String> files = minIOService.listFiles();
            log.info("Found {} files in MinIO.", files.size());

            for (String fileName : files) {
                log.debug("Processing file: {}", fileName);

                if (!fileName.startsWith("Bill_")) {
                    log.debug("Skipping non-bill file: {}", fileName);
                    continue;
                }

                if (billMetadataService.existsByFileName(fileName)) {
                    log.debug("Metadata already exists for file: {}", fileName);
                    continue;
                }

                String[] parts = fileName.replace("Bill_", "").replace(".pdf", "").split("_");

                if (parts.length != 4) {
                    log.warn("Skipping malformed filename: {}", fileName);
                    continue;
                }

                try {
                    String type = "bill";
                    String counterparty = parts[0];
                    String period = parts[1];
                    BigDecimal amount = new BigDecimal(parts[2].replace(",", "."));
                    String currency = parts[3];

                    BillMetadata billMetadata = new BillMetadata(type, counterparty, period, amount, currency, fileName);
                    billMetadataService.saveBillMetadataIfNotExists(billMetadata);

                    log.info("Inserted metadata for: {}", fileName);
                } catch (Exception ex) {
                    log.error("Error processing file: {}", fileName, ex);
                }
            }
        } catch (Exception e) {
            log.error("Error processing files in MinIO", e);
        }
    }
}