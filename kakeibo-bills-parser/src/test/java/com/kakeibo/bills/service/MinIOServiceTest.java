package com.kakeibo.bills.service;

import com.kakeibo.bills.config.MinIOConfig;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.ErrorResponse;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for the {@link MinIOService} class.
 * <p>
 * This test verifies the behavior of the methods in the {@link MinIOService} class for interacting with MinIO.
 * The tests cover the following functionalities:
 * <p>
 * - {@link MinIOService#uploadFile(String, InputStream, long)}: Verifies that the file upload to MinIO occurs
 *   and the {@link MinioClient#putObject(PutObjectArgs)} method is called correctly.
 * <p>
 * - {@link MinIOService#downloadFile(String)}: Ensures that the file download from MinIO works,
 *   and the {@link MinioClient#getObject(GetObjectArgs)} method is called correctly.
 * <p>
 * - {@link MinIOService#deleteFile(String)}: Validates that file deletion from MinIO is handled,
 *   and the {@link MinioClient#removeObject(RemoveObjectArgs)} method is called correctly.
 */
class MinIOServiceTest {

    private MinIOService minIOService;
    private MinioClient minioClient;
    private MinIOConfig minIOConfig;

    @BeforeEach
    void setUp() {
        minioClient = mock(MinioClient.class);
        minIOConfig = mock(MinIOConfig.class);
        when(minIOConfig.getBucket()).thenReturn("test-bucket");

        minIOService = new MinIOService(minioClient, minIOConfig);
    }

    @Test
    void testUploadFile() {
        InputStream testStream = new ByteArrayInputStream("TestData".getBytes());

        assertDoesNotThrow(() -> minIOService.uploadFile("test.pdf", testStream, 1024));

        try {
            verify(minioClient, times(1)).putObject(any(PutObjectArgs.class));
        } catch (ErrorResponseException | InsufficientDataException | InternalException |
                 InvalidResponseException | XmlParserException | ServerException | IOException e) {
            fail("Exception thrown during verification: " + e.getMessage());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDownloadFile() throws Exception {
        InputStream testStream = new ByteArrayInputStream("TestData".getBytes());

        GetObjectResponse mockResponse = mock(GetObjectResponse.class);
        when(mockResponse.readAllBytes()).thenReturn("TestData".getBytes());
        when(minioClient.getObject(any(GetObjectArgs.class))).thenReturn(mockResponse);

        InputStream result = minIOService.downloadFile("test.pdf");

        assertNotNull(result, "Downloaded file should not be null");
        verify(minioClient, times(1)).getObject(any(GetObjectArgs.class));
    }

    @Test
    void testDeleteFile() {
        assertDoesNotThrow(() -> minIOService.deleteFile("test.pdf"));

        try {
            verify(minioClient, times(1)).removeObject(any(RemoveObjectArgs.class));
        } catch (ErrorResponseException | InsufficientDataException | InternalException |
                 InvalidResponseException | XmlParserException | ServerException | IOException e) {
            fail("Exception thrown during verification: " + e.getMessage());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDownloadFile_NotFound() throws Exception {
        // Create a mock ErrorResponse object
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponse.code()).thenReturn("NoSuchKey"); // Simulate file not found error

        // Create a mock Response object to simulate MinIO's HTTP response
        Response response = new Response.Builder()
                .request(new okhttp3.Request.Builder().url("http://localhost").build())
                .protocol(okhttp3.Protocol.HTTP_1_1)
                .code(404)
                .message("Not Found")
                .build();

        // Mock MinIO to throw an ErrorResponseException when trying to download a missing file
        ErrorResponseException exception = new ErrorResponseException(errorResponse, response, "test-bucket");

        when(minioClient.getObject(any(GetObjectArgs.class))).thenThrow(exception);

        // Verify that attempting to download a missing file throws the expected exception
        assertThrows(ErrorResponseException.class, () -> minIOService.downloadFile("missing.pdf"));

        // Ensure MinIO's `getObject` method was called once
        verify(minioClient, times(1)).getObject(any(GetObjectArgs.class));
    }
}