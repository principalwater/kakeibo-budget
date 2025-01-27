package com.kakeibo.scraper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RaiffeisenScraper implements BankScraper {

    @Override
    public List<ParsedTransaction> parseTransactions(String filePath) {
        List<ParsedTransaction> transactions = new ArrayList<>();

        try (Reader reader = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("Date", "Description", "Amount")
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord record : records) {
                ParsedTransaction transaction = new ParsedTransaction();
                transaction.setDate(LocalDate.parse(record.get("Date"))); // Assuming "Date" is in ISO format
                transaction.setDescription(record.get("Description"));
                transaction.setAmount(Double.parseDouble(record.get("Amount")));
                transactions.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing Raiffeisen Bank file", e);
        }

        return transactions;
    }
}