package com.kakeibo.bills.service;

import com.kakeibo.bills.model.BillMetadata;
import com.kakeibo.bills.repository.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

/**
 * Unit test for the {@link BillService} class.
 * <p>
 * This test checks that the {@link BillService#saveBillMetadata(BillMetadata)} method correctly
 * invokes the {@link BillRepository#save(BillMetadata)} method once with the provided bill metadata.
 * It mocks the repository and verifies the interaction to ensure proper behavior.
 */
class BillServiceTest {

    @InjectMocks
    private BillService billService;

    @Mock
    private BillRepository billRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBillMetadata() {
        BillMetadata bill = new BillMetadata(
                "Теплосетьэнерго",
                "2024-11-01",
                new BigDecimal("2945.79"),
                "RUB",
                "Bill_Теплосетьэнерго_2024-11-01_2945,79_RUB.pdf"
        );

        billService.saveBillMetadata(bill);

        verify(billRepository, times(1)).save(bill);
    }
}