package com.kakeibo.bills.service;

import com.kakeibo.bills.model.BillMetadata;
import com.kakeibo.bills.repository.BillMetadataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for the {@link BillMetadataService} class.
 */
class BillMetadataServiceTest {

    @InjectMocks
    private BillMetadataService billMetadataService;

    @Mock
    private BillMetadataRepository billMetadataRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByFileName_FileExists() {
        String fileName = "Bill_Теплосетьэнерго_2024-11_2945,79_RUB.pdf";
        BillMetadata mockMetadata = new BillMetadata("bill", "Теплосетьэнерго", "2024-11-01",
                new BigDecimal("2945.79"), "RUB", fileName);

        when(billMetadataRepository.findByFileName(fileName)).thenReturn(Optional.of(mockMetadata));

        Optional<BillMetadata> foundMetadata = billMetadataService.getBillMetadataByFileName(fileName);
        assertTrue(foundMetadata.isPresent(), "Metadata should be found");
        assertEquals(fileName, foundMetadata.get().getFileName(), "File name should match");
    }

    @Test
    void testFindByFileName_FileNotExists() {
        String fileName = "NonExistentFile.pdf";

        when(billMetadataRepository.findByFileName(fileName)).thenReturn(Optional.empty());

        Optional<BillMetadata> foundMetadata = billMetadataService.getBillMetadataByFileName(fileName);
        assertFalse(foundMetadata.isPresent(), "Metadata should not be found");
    }

    @Test
    void testSaveBillMetadataIfNotExists_NewFile() {
        String fileName = "Bill_Теплосетьэнерго_2024-11_2945,79_RUB.pdf";
        BillMetadata newMetadata = new BillMetadata("bill", "Теплосетьэнерго", "2024-11-01",
                new BigDecimal("2945.79"), "RUB", fileName);

        when(billMetadataRepository.findByFileName(fileName)).thenReturn(Optional.empty());
        when(billMetadataRepository.save(any(BillMetadata.class))).thenReturn(newMetadata);

        boolean wasSaved = billMetadataService.saveBillMetadataIfNotExists(newMetadata);
        assertTrue(wasSaved, "New metadata should be saved");
        verify(billMetadataRepository, times(1)).save(newMetadata);
    }

    @Test
    void testSaveBillMetadataIfNotExists_ExistingFile() {
        String fileName = "Bill_Теплосетьэнерго_2024-11_2945,79_RUB.pdf";
        BillMetadata existingMetadata = new BillMetadata("bill", "Теплосетьэнерго", "2024-11-01",
                new BigDecimal("2945.79"), "RUB", fileName);

        // Mock repository to return existing metadata
        when(billMetadataRepository.findByFileName(fileName)).thenReturn(Optional.of(existingMetadata));

        boolean wasSaved = billMetadataService.saveBillMetadataIfNotExists(existingMetadata);

        assertFalse(wasSaved, "Metadata should not be saved again");

        verify(billMetadataRepository, never()).save(any(BillMetadata.class));
    }
}