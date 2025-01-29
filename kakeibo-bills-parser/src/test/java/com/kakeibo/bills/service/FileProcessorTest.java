package com.kakeibo.bills.service;

import com.kakeibo.bills.model.BillMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for the {@link FileProcessor} class.
 * <p>
 * This test verifies the behavior of the {@link FileProcessor#processFiles()} method. It mocks the
 * {@link BillService} and {@link MinIOService} dependencies to test the file processing logic.
 * The test ensures that the correct interactions with the mocked services occur:
 * - {@link MinIOService#uploadFile(String, InputStream, long)} is called once with the correct file.
 * - {@link BillService#saveBillMetadata(BillMetadata)} is called once with the correct bill metadata.
 * <p>
 * The test also ensures that the file is not deleted after processing, as the file deletion flag is set to false.
 */
class FileProcessorTest {

    private FileProcessor fileProcessor;
    private BillService billService;
    private MinIOService minIOService;
    private static final String TEST_DIRECTORY = "src/test/resources/bills";

    @BeforeEach
    void setUp() {
        billService = mock(BillService.class);
        minIOService = mock(MinIOService.class);

        // Pass `false` to prevent file deletion
        fileProcessor = new FileProcessor(billService, minIOService, TEST_DIRECTORY, false);
    }

    @Test
    void testProcessFiles() throws Exception {
        // Mock file with correct path
        Path testFilePath = Paths.get(TEST_DIRECTORY, "Bill_Теплосетьэнерго_2024-11-01_2945,79_RUB.pdf");
        File testFile = testFilePath.toFile();

        // Ensure the file exists before processing
        assert testFile.exists() : "Test PDF file does not exist!";

        // Mock InputStream from File
        InputStream fileInputStream = new FileInputStream(testFile);

        // Corrected MinIOService uploadFile Mock
        doNothing().when(minIOService).uploadFile(anyString(), any(InputStream.class), anyLong());

        // Corrected `saveBillMetadata` Mock
        doNothing().when(billService).saveBillMetadata(any(BillMetadata.class));

        // Invoke method
        fileProcessor.processFiles();

        // Verify MinIOService was called correctly
        verify(minIOService, times(1)).uploadFile(
                eq(testFile.getName()), any(InputStream.class), anyLong()
        );

        // Verify BillService was called with Correct Argument Type
        verify(billService, times(1)).saveBillMetadata(any(BillMetadata.class));

        // Ensure file still exists after test execution
        assert testFile.exists() : "Test PDF file was deleted!";
    }
}