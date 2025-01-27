package com.kakeibo.scraper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OtherBankScraper implements BankScraper {

    @Override
    public List<ParsedTransaction> parseTransactions(String filePath) {
        List<ParsedTransaction> transactions = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ParsedTransaction[] parsedArray = objectMapper.readValue(new File(filePath), ParsedTransaction[].class);

            transactions.addAll(Arrays.asList(parsedArray));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing Other Bank file", e);
        }

        return transactions;
    }
}