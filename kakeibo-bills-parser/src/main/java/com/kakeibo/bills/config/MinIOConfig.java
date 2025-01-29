package com.kakeibo.bills.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.minio")
public class MinIOConfig {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;

    // TODO: remove after debug
    @PostConstruct
    public void init() {
        System.out.println("MinIO Endpoint: " + endpoint);
        System.out.println("MinIO Access Key: " + accessKey);
        System.out.println("MinIO Secret Key: " + secretKey);
        System.out.println("MinIO Bucket: " + bucket);
    }

    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }

    public String getAccessKey() { return accessKey; }
    public void setAccessKey(String accessKey) { this.accessKey = accessKey; }

    public String getSecretKey() { return secretKey; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }

    public String getBucket() { return bucket; }
    public void setBucket(String bucket) { this.bucket = bucket; }
}