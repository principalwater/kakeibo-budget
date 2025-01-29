package com.kakeibo.bills.service;

import com.kakeibo.bills.config.MinIOConfig;
import io.minio.*;
import io.minio.errors.MinioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinIOService {
    private static final Logger log = LoggerFactory.getLogger(MinIOService.class);
    private final MinioClient minioClient;
    private final String bucketName;

    public MinIOService(MinioClient minioClient, MinIOConfig minIOConfig) {
        this.minioClient = minioClient;
        this.bucketName = minIOConfig.getBucket();
    }

    public void uploadFile(String fileName, InputStream inputStream, long size) {
        try {
            log.info("Uploading file to MinIO: {}", fileName);

            // Ensure bucket exists
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("Created MinIO bucket: {}", bucketName);
            }

            // Upload file
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, size, -1)
                            .contentType("application/pdf")
                            .build()
            );

            log.info("File uploaded successfully: {}", fileName);
        } catch (Exception e) {
            log.error("Error uploading file to MinIO", e);
        }
    }

    public InputStream downloadFile(String fileName) throws MinioException, InvalidKeyException, NoSuchAlgorithmException, IOException {
        return minioClient.getObject(
                GetObjectArgs.builder().bucket(bucketName).object(fileName).build()
        );
    }

    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build()
            );
        } catch (Exception e) {
            log.error("Error deleting file from MinIO", e);
        }
    }
}