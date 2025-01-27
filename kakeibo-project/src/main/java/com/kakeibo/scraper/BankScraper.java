package com.kakeibo.scraper;

import java.util.List;

public interface BankScraper {

    /**
     * Parses transactions from a bank data file.
     *
     * @param filePath the path to the bank data file
     * @return a list of parsed transactions
     */
    List<ParsedTransaction> parseTransactions(String filePath);
}