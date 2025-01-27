package com.kakeibo.scraper;

import com.kakeibo.model.MoneyStateModel;
import com.kakeibo.model.PaymentModel;
import com.kakeibo.utils.PaymentKind;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GenericScraper {

    protected WebDriver driver;

    public GenericScraper(WebDriver driver) {
        this.driver = driver;
    }

    public abstract String getProviderName();

    public abstract List<MoneyStateModel> scrapeAccounts();

    public abstract List<PaymentModel> scrapeStatements(LocalDate startFrom);

    protected void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"), "complete")
        );
    }

    protected WebElement waitForElement(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected List<WebElement> waitForElements(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected void login(String username, String password) {
        driver.get("https://online.raiffeisen.ru/");

        WebElement usernameField = waitForElement(By.xpath("//input[contains(@placeholder, 'Login')]"), 60);
        WebElement passwordField = driver.findElement(By.xpath("//input[contains(@placeholder, 'Password')]"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        passwordField.sendKeys(Keys.ENTER);

        // Wait for dashboard or next screen to load
        waitForPageLoad();
    }

    protected List<PaymentModel> parseOfx(File ofxFile, String accountName) throws Exception {
        List<PaymentModel> payments = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(ofxFile);

        var transactions = document.getElementsByTagName("STMTTRN");
        for (int i = 0; i < transactions.getLength(); i++) {
            var transaction = transactions.item(i);

            var datePosted = transaction.getChildNodes().item(1).getTextContent();
            var amount = Double.parseDouble(transaction.getChildNodes().item(3).getTextContent());
            var description = transaction.getChildNodes().item(5).getTextContent();

            LocalDate date = LocalDate.parse(datePosted.substring(0, 8), DateTimeFormatter.ofPattern("yyyyMMdd"));
            payments.add(new PaymentModel(
                    date,                // LocalDate
                    accountName,         // String
                    description,         // String
                    amount,              // double
                    PaymentKind.EXPENSE, // TODO: Change, now assuming default is EXPENSE
                    "RUB",               // TODO: Replace with actual currency if available
                    "some-statement-id"  // TODO: Replace with actual statement reference if available
            ));
        }

        return payments;
    }
}