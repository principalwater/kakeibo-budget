package com.kakeibo.scraper;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class RaiffeisenScraperTest {

    @Test
    void testParseTransactions() {
        BankScraper scraper = new RaiffeisenScraper();

        // Use classpath to load the test file
        String filePath = Objects.requireNonNull(
                getClass().getClassLoader().getResource("banks/raiffeisen-sample.csv")
        ).getPath();

        List<ParsedTransaction> transactions = scraper.parseTransactions(filePath);

        assertThat(transactions).isNotEmpty();
        assertThat(transactions.get(0).getDescription()).isEqualTo("Groceries");
    }
}