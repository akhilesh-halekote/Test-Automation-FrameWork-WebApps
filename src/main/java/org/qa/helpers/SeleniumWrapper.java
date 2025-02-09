package org.qa.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.qa.driverfactory.WebDriverFactory;

import java.time.Duration;


public class SeleniumWrapper {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);
    private static final Duration POLLING_INTERVAL = Duration.ofMillis(500);


    public static void clickOnElement(WebElement element) {
        element.click();
    }

    public static void clickOnLink(WebElement element) {
        try {
            String href = element.getDomAttribute("href");
            if (href == null || href.isEmpty()) {
                throw new AssertionError("[ClickLink] Link does not have a valid href attribute");
            }
            element.click();
            } catch (Exception e) {
            System.out.println("[ClickLink] Error during link click operation");
            throw e;
            }
    }
    public static WebElement waitForElementToBeVisible(WebElement element) {
        try {
            System.out.println("[WaitManager] Waiting for element to be visible.");
            return getFluentWait().until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            System.out.println("[WaitManager] Timeout while waiting for element visibility.");
            throw e;
        }
    }

    private static Wait<WebDriver> getFluentWait() {
        return new FluentWait<>(WebDriverFactory.getWebDriver())
                .withTimeout(DEFAULT_TIMEOUT)
                .pollingEvery(POLLING_INTERVAL)
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
    }

    private static JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) WebDriverFactory.getWebDriver();
    }

    public static String getInnerText(WebElement element) {
        waitForElementToBeVisible(element);
        try {
            System.out.println("[GetInnerText] Executing script to retrieve inner text for element");
            String innerText = (String) getJavascriptExecutor().executeScript(
                    "return arguments[0].innerText;", element
            );
            System.out.println("[GetInnerText] Inner text retrieved successfully: " + innerText);
            return innerText;
        } catch (Exception e) {
            System.out.println("[GetInnerText] Error retrieving inner text for element");
            throw e;
        }
    }

}
