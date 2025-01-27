package com.kakeibo.scraper;

import com.kakeibo.model.MoneyStateModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RaiffeisenScraperTest {

    private WebDriver driver;
    private RaiffeisenScraper scraper;

    @BeforeEach
    void setUp() {
        // Set the ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");

        // Specify the Chrome binary location
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");

        // Initialize WebDriver
        driver = new ChromeDriver(options);

        // Replace with valid credentials
        String username = "test_username";
        String password = "test_password";

        // Initialize the scraper with WebDriver and credentials
        scraper = new RaiffeisenScraper(driver, username, password);
    }

    @AfterEach
    void tearDown() {
        // Quit the WebDriver after the test
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testScrapeAccounts() {
        // Invoke the scrapeAccounts method
        List<MoneyStateModel> accounts = scraper.scrapeAccounts();

        // Assertions
        assertNotNull(accounts, "Accounts list should not be null");
        assertFalse(accounts.isEmpty(), "Accounts list should not be empty");
    }
}