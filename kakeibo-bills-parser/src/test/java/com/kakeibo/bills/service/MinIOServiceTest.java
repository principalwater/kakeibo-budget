package com.kakeibo.bills.service;

import com.kakeibo.bills.config.MinIOConfig;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link MinIOService}.
 */
class MinIOServiceTest {

    @Mock
    private MinioClient minioClient;

    @Mock
    private MinIOConfig minIOConfig;

    private MinIOService minIOService;
    private static final String BUCKET_NAME = "test-bucket";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(minIOConfig.getBucket()).thenReturn(BUCKET_NAME);
        minIOService = new MinIOService(minioClient, minIOConfig);
    }

    @Test
    void testListFiles() throws Exception {
        // Mock MinIO listObjects response
        Result<Item> result1 = mock(Result.class);
        Result<Item> result2 = mock(Result.class);
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        when(item1.objectName()).thenReturn("Bill_TestCompany_2024-11_1500,50_USD.pdf");
        when(item2.objectName()).thenReturn("Bill_AnotherCompany_2024-12_2500,00_EUR.pdf");
        when(result1.get()).thenReturn(item1);
        when(result2.get()).thenReturn(item2);

        Iterable<Result<Item>> mockIterable = List.of(result1, result2);
        when(minioClient.listObjects(any(ListObjectsArgs.class))).thenReturn(mockIterable);

        // Execute test
        List<String> files = minIOService.listFiles();

        // Assertions
        assertNotNull(files);
        assertEquals(2, files.size());
        assertTrue(files.contains("Bill_TestCompany_2024-11_1500,50_USD.pdf"));
        assertTrue(files.contains("Bill_AnotherCompany_2024-12_2500,00_EUR.pdf"));
    }

    @Test
    void testDownloadFile() throws Exception {
        // Mock MinIO getObject response
        InputStream testStream = new ByteArrayInputStream("TestData".getBytes());
        GetObjectResponse mockResponse = mock(GetObjectResponse.class);

        when(mockResponse.readAllBytes()).thenReturn("TestData".getBytes());
        when(minioClient.getObject(any(GetObjectArgs.class))).thenReturn(mockResponse);

        // Execute test
        InputStream result = minIOService.downloadFile("Bill_TestCompany_2024-11_1500,50_USD.pdf");

        // Assertions
        assertNotNull(result);
        assertEquals("TestData", new String(result.readAllBytes()));
    }
}