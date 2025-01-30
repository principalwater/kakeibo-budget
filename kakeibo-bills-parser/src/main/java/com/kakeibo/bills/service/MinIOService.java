package com.kakeibo.bills.service;

import com.kakeibo.bills.config.MinIOConfig;
import io.minio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MinIOService {
    private static final Logger log = LoggerFactory.getLogger(MinIOService.class);

    private final MinioClient minioClient;
    private final String bucketName;

    public MinIOService(MinioClient minioClient, MinIOConfig minIOConfig) {
        this.minioClient = minioClient;
        this.bucketName = minIOConfig.getBucket();
    }

    /**
     * Lists all files stored in the configured MinIO bucket.
     *
     * @return List of file names.
     */
    public List<String> listFiles() {
        log.info("Listing files in bucket: {}", bucketName);
        try {
            return StreamSupport.stream(
                            minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build()).spliterator(), false
                    ).map(result -> {
                        try {
                            return result.get().objectName();
                        } catch (Exception e) {
                            log.error("Error retrieving object name from MinIO response: {}", e.getMessage());
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Unexpected error while listing files", e);
        }
        return List.of();
    }

    /**
     * Downloads a file from MinIO.
     *
     * @param fileName The name of the file to download.
     * @return InputStream of the file.
     * @throws Exception If an error occurs while fetching the file.
     */
    public InputStream downloadFile(String fileName) throws Exception {
        log.info("Downloading file from MinIO: {}", fileName);
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
    }
}