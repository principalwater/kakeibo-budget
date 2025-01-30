package com.kakeibo.bills;

import com.kakeibo.bills.config.MinIOConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.kakeibo.bills")
@EnableConfigurationProperties(MinIOConfig.class)
public class KakeiboBillsParserApplication {
    public static void main(String[] args) {
        SpringApplication.run(KakeiboBillsParserApplication.class, args);
    }
}