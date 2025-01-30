package com.kakeibo.bills.service;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class DBInitService {

    private final JdbcTemplate jdbcTemplate;

    public DBInitService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Reads and executes the `init.sql` script from resources/db/init.sql.
     */
    @PostConstruct
    public void initializeDatabase() {
        try {
            String sql = new BufferedReader(new InputStreamReader(
                    new ClassPathResource("db/init.sql").getInputStream(), StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));

            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database from init.sql", e);
        }
    }
}