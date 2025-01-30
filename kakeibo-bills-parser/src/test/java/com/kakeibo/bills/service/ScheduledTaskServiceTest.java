package com.kakeibo.bills.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Unit test for {@link ScheduledTaskService}.
 * <p>
 * - Ensures that new files in MinIO are processed and stored only if they do not exist in the database.
 * - Mocks MinIOService to simulate file retrieval.
 * - Mocks BillMetadataService to validate that existing metadata is not duplicated.
 */
class ScheduledTaskServiceTest {

    @InjectMocks
    private ScheduledTaskService scheduledTaskService;

    @Mock
    private MinIOService minIOService;

    @Mock
    private BillMetadataService billMetadataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessNewFiles_AddsOnlyNewFiles() throws Exception {
        String existingFile = "Bill_ExistingCompany_2024-10_1000,75_EUR.pdf";
        String newFile = "Bill_TestCompany_2024-11_1500,50_USD.pdf";

        // Mock: Existing file already in DB
        when(billMetadataService.existsByFileName(existingFile)).thenReturn(true);

        // Mock: New file does not exist
        when(billMetadataService.existsByFileName(newFile)).thenReturn(false);

        // Ensure `saveBillMetadataIfNotExists` is called with ANY valid `BillMetadata`
        when(billMetadataService.saveBillMetadataIfNotExists(argThat(metadata ->
                metadata.getFileName().equals(newFile))))
                .thenReturn(true);

        // Mock MinIOService to return both files
        when(minIOService.listFiles()).thenReturn(List.of(existingFile, newFile));

        // Execute method
        scheduledTaskService.processNewFiles();

        // Verify that `saveBillMetadataIfNotExists` was called ONLY for the new file
        verify(billMetadataService, times(1)).saveBillMetadataIfNotExists(argThat(metadata ->
                metadata.getFileName().equals(newFile)));

        // Ensure `saveBillMetadataIfNotExists` was NOT called for existing file
        verify(billMetadataService, never()).saveBillMetadataIfNotExists(argThat(metadata ->
                metadata.getFileName().equals(existingFile)));
    }
}