package com.kakeibo.bills.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOClientConfig {
    private final MinIOConfig minIOConfig;

    public MinIOClientConfig(MinIOConfig minIOConfig) {
        this.minIOConfig = minIOConfig;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minIOConfig.getEndpoint())
                .credentials(minIOConfig.getAccessKey(), minIOConfig.getSecretKey())
                .build();
    }
}