package com.kakeibo.bills.controller;

import com.kakeibo.bills.service.ScheduledTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AdminController provides administrative endpoints to manually trigger scheduled tasks.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ScheduledTaskService scheduledTaskService;

    public AdminController(ScheduledTaskService scheduledTaskService) {
        this.scheduledTaskService = scheduledTaskService;
    }

    /**
     * Manually trigger the MinIO polling process.
     *
     * @return ResponseEntity with a success message.
     */
    @PostMapping("/trigger-polling")
    public ResponseEntity<String> triggerPolling() {
        scheduledTaskService.processNewFiles();
        return ResponseEntity.ok("Polling triggered successfully.");
    }
}