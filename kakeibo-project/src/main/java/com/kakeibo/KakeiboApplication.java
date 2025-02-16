package com.kakeibo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KakeiboApplication {

    public static void main(String[] args) {
        SpringApplication.run(KakeiboApplication.class, args);
        System.out.println("Kakeibo Budget Application is running!");
    }
}