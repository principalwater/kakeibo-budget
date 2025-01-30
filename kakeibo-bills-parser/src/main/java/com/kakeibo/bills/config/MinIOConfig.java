package com.kakeibo.bills.config;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.minio")
public class MinIOConfig {
    private static final Logger log = LoggerFactory.getLogger(MinIOConfig.class);

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;

    public String getEndpoint() { return endpoint; }
    public String getAccessKey() { return accessKey; }
    public String getSecretKey() { return secretKey; }
    public String getBucket() { return bucket; }

    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    public void setAccessKey(String accessKey) { this.accessKey = accessKey; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
    public void setBucket(String bucket) { this.bucket = bucket; }

    @Bean
    public MinioClient minioClient() {
        log.info("Creating MinIO client for endpoint: {}", endpoint);
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}