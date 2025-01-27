package com.kakeibo.scraper;

import com.kakeibo.model.MoneyStateModel;
import com.kakeibo.model.PaymentModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RaiffeisenScraper extends GenericScraper {

    private final String username;
    private final String password;

    public RaiffeisenScraper(WebDriver driver, String username, String password) {
        super(driver);
        this.username = username;
        this.password = password;
    }

    @Override
    public String getProviderName() {
        return "Raiffeisen";
    }

    @Override
    public List<MoneyStateModel> scrapeAccounts() {
        login(username, password);

        driver.get("https://online.raiffeisen.ru/#/accounts");
        waitForPageLoad();

        List<WebElement> accountElements = waitForElements(By.className("account-widget"), 30);
        List<MoneyStateModel> accounts = new ArrayList<>();

        for (WebElement account : accountElements) {
            try {
                String name = account.findElement(By.className("product-header-title__name-text"))
                        .getAttribute("textContent");

                String amountText = account.findElement(By.className("product-header-info__value"))
                        .getAttribute("textContent")
                        .replaceAll("[^\\d,]", "");
                double amount = Double.parseDouble(amountText.replace(",", "."));

                String currency = account.findElement(By.className("amount__symbol"))
                        .getAttribute("textContent");

                accounts.add(new MoneyStateModel(name, amount, currency));
            } catch (Exception e) {
                System.err.println("Error parsing account: " + e.getMessage());
            }
        }

        return accounts;
    }

    @Override
    public List<PaymentModel> scrapeStatements(LocalDate startFrom) {
        // Implement statement scraping similar to above
        return null;
    }
}