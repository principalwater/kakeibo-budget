package com.kakeibo.bills.controller;

import com.kakeibo.bills.model.BillMetadata;
import com.kakeibo.bills.service.BillMetadataService;
import com.kakeibo.bills.service.MinIOService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bills")
@CrossOrigin(origins = "*") // Allow requests from external clients
public class BillController {

    private final MinIOService minIOService;
    private final BillMetadataService billMetadataService;

    public BillController(MinIOService minIOService, BillMetadataService billMetadataService) {
        this.minIOService = minIOService;
        this.billMetadataService = billMetadataService;
    }

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Bills API is running.");
    }

    @GetMapping("/{fileName}/download")
    public ResponseEntity<InputStreamResource> downloadBill(@PathVariable String fileName) {
        try {
            InputStream inputStream = minIOService.downloadFile(fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/metadata")
    public ResponseEntity<List<BillMetadata>> getAllBillsMetadata() {
        return ResponseEntity.ok(billMetadataService.getAllBillMetadata());
    }

    @GetMapping("/metadata/{fileName}")
    public ResponseEntity<BillMetadata> getBillMetadata(@PathVariable String fileName) {
        Optional<BillMetadata> billMetadata = billMetadataService.getBillMetadataByFileName(fileName);
        return billMetadata.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}