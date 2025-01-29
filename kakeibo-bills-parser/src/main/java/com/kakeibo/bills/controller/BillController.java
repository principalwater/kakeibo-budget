package com.kakeibo.bills.controller;

import com.kakeibo.bills.service.MinIOService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
@RequestMapping("/bills")
public class BillController {

    private final MinIOService minIOService;

    public BillController(MinIOService minIOService) {
        this.minIOService = minIOService;
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
}