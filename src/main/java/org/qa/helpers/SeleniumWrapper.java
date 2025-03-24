package org.qa.helpers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.qa.driverfactory.WebDriverFactory;
import org.qa.listeners.TestListener;

import java.time.Duration;
import java.util.Set;

/**
 * This class has all methods to perform necessary actions on a web page
 * All these methods are wrappers on top of Selenium Provided methods, to perform actions gracefully
 * if some actions are missing please feel to add it here
 *
 */
@Slf4j
public class SeleniumWrapper {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);
    private static final Duration POLLING_INTERVAL = Duration.ofMillis(500);
    private static final String ERR_MESSAGE = "[SeleniumWrapper] No element was found with locator {}";


    public static <T> T getPage(Class<T> pageClass) {
        WebDriver driver = TestListener.getDriver();
        return PageFactory.initElements(driver, pageClass);
    }


    /**
     * Click on web element
     * @param element - WebElement
     */
    public static void clickOnElement(WebElement element) {
        try {
            element.click();
        } catch (NoSuchElementException ne){
            log.error(ERR_MESSAGE, element);
        }
    }

    /**
     * Click on link using href attribute
     * @param element - WebElement
     */
    public static void clickOnLink(WebElement element) {
        try {
            String href = element.getDomAttribute("href");
            if (href == null || href.isEmpty()) {
                throw new AssertionError("[SeleniumWrapper] Link does not have a valid href attribute");
            }
            element.click();
        } catch (Exception e) {
            log.error("[SeleniumWrapper] Error during link click operation on {}", element);
            throw e;
        }
    }

    /**
     * Wait until the web element is visible
     * @param element - WebElement
     * @return WebElement
     */
    public static WebElement waitForElementToBeVisible(WebElement element) {
        try {
            log.info("[SeleniumWrapper] Waiting for element to be visible.");
            return getFluentWait().until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            log.error("[SeleniumWrapper] Timeout while waiting for element visibility.");
            throw e;
        }
    }


    /**
     * Fluent wait method
     * @return Wait Object
     */
    private static Wait<WebDriver> getFluentWait() {
        return new FluentWait<>(WebDriverFactory.getWebDriver())
                .withTimeout(DEFAULT_TIMEOUT)
                .pollingEvery(POLLING_INTERVAL)
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
    }

    /**
     * Java Script Executor
     * @return JavaScriptExecutor
     */
    private static JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) WebDriverFactory.getWebDriver();
    }

    /**
     * Get inner text of a WebElement
     * @param element WebElement
     * @return String
     */
    public static String getInnerText(WebElement element) {
        waitForElementToBeVisible(element);
        try {
            log.info("[SeleniumWrapper] Executing script to retrieve inner text for element");
            return (String) getJavascriptExecutor().executeScript(
                    "return arguments[0].innerText;", element
            );
        } catch (Exception e) {
            log.error("[SeleniumWrapper] Error retrieving inner text for element");
            throw e;
        }
    }


    /**
     * Clear input box
     * @param element WebElement
     */
    public static void clearInputBox(WebElement element) {
        try{
            element.clear();
        } catch (NoSuchElementException ne){
            log.error(ERR_MESSAGE, element);
            throw ne;
        }

    }


    /**
     * Input text in to the textbox
     * @param element WebElement
     * @param text String
     */
    public static void inputText(WebElement element, String text) {
        try{
            clickOnElement(element);
            clearInputBox(element);
            element.sendKeys(text);
        } catch (NoSuchElementException ne){
            log.error(ERR_MESSAGE, element);
        }

    }

    /**
     * Check if the element is selected
     * @param element WebElement
     * @return boolean
     */
    public static boolean isSelected(WebElement element) {
        try {
            return waitForElementToBeVisible(element).isSelected();
        } catch (NoSuchElementException e) {
            log.error(ERR_MESSAGE, element);
            return false;
        }
    }


    /**Check if the element is enabled
     * @param element WebElement
     * @return boolean
     */

    public static boolean isEnabled(WebElement element) {
        try {
            return waitForElementToBeVisible(element).isEnabled();
        } catch (NoSuchElementException e) {
            log.error(ERR_MESSAGE, element);
            return false;
        }
    }


    /**
     * Check if the element is displayed
     * @param element WebElement
     * @return boolean
     */
    public static boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            log.error(ERR_MESSAGE, element);
            return false;
        }
    }


    /**
     * Check if the element exists
     * @param element WebElement
     * @return boolean
     */
    public static boolean isElementExists(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            log.error(ERR_MESSAGE, element);
            return false;
        }
    }


    /**
     * Get Action object
     * @return Action object
     */
    private static Actions getActions() {
        return new Actions(WebDriverFactory.getWebDriver());
    }


    /**
     * Move cursor to element
     * @param element WebElement
     */
    public static void moveToElement(WebElement element) {
        try {
            log.debug("[SeleniumWrapper] Waiting for element to be visible");
            WebElement visibleElement = waitForElementToBeVisible(element);
            log.debug("[SeleniumWrapper] Moving to the element");
            getActions().scrollToElement(visibleElement).perform();
            getActions().moveToElement(visibleElement).perform();
            log.debug("[SeleniumWrapper] Moved to element successfully");
        } catch (TimeoutException e) {
            log.error("[SeleniumWrapper] Timeout while waiting for element to be visible");
            throw e;
        } catch (Exception e) {
            log.error("[SeleniumWrapper] Error during move to element operation");
            throw e;
        }
    }

    /**
     * Scroll to the element
     * @param element WebElement
     */
    public static void scrollToElement(WebElement element) {
        try {
            getJavascriptExecutor().executeScript("arguments[0].scrollIntoView(true);", waitForElementToBeVisible(element));
        } catch (Exception e) {
            log.error("[SeleniumWrapper] There was an error while trying to scroll to an element");
            throw e;
        }
    }

    /**
     * Moving cursor to element using X, Y coordinate
     * @param element WebElement
     * @param offsetX - X Co ordinate
     * @param offsetY - Y Co ordinate
     */
    public static void moveCursorToElement(WebElement element, int offsetX, int offsetY) {
        Actions actions = getActions();
        actions.moveToElement(element, offsetX, offsetY).perform();
    }


    /**
     * Get the current url of the webpage
     * @return String
     */
    public static String getCurrentUrl() {
        try {
            String currentUrl = WebDriverFactory.getWebDriver().getCurrentUrl();
            log.debug("[SeleniumWrapper] Current URL: \"{}\"", currentUrl);
            return currentUrl;
        } catch (Exception e) {
            log.error("[SeleniumWrapper] Error retrieving current URL");
            throw e;
        }
    }

    /**
     * Get the page title
     * @return String
     */
    public static String getPageTitle() {
        try {
            String title = WebDriverFactory.getWebDriver().getTitle();
            log.debug("[SeleniumWrapper] Page title: \"{}\"", title);
            return title;
        } catch (Exception e) {
            log.error("[SeleniumWrapper] Error retrieving page title : \"{}\"", e.getMessage());
            return "";
        }
    }


    /**
     * Switch to new window using window handle
     */
    public static void switchToNewWindow() {
        WebDriver driver = WebDriverFactory.getWebDriver();
        try {
            Set<String> windowHandles = driver.getWindowHandles();
            String currentWindowHandle = driver.getWindowHandle();
            for (String handle : windowHandles) {
                if (!handle.equals(currentWindowHandle)) {
                    driver.switchTo().window(handle);
                    log.debug("[SwitchToNewTab] Switched to new tab with handle: {}", handle);
                    return;
                }
            }
            log.warn("[SeleniumWrapper] No new tab found to switch to");
            throw new RuntimeException("[SeleniumWrapper] No new tab found to switch to");
        } catch (Exception e) {
            log.error("[SeleniumWrapper] Error occurred while switching to new tab", e);
            throw e;
        }
    }


    /**
     * Close currently opened window
     */
    public static void closeCurrentWindowAndSwitchToMain() {
        WebDriver driver = WebDriverFactory.getWebDriver();
        try {
            Set<String> windowHandles = driver.getWindowHandles();
            String currentWindowHandle = driver.getWindowHandle();
            String mainWindowHandle = windowHandles.iterator().next(); // Get the first (main) window handle

            // If the current window is the main window, do nothing
            if (currentWindowHandle.equals(mainWindowHandle)) {
                log.debug("[CloseWindow] Current window is the main window. No action taken.");
                return;
            }

            driver.close();
            log.debug("[SeleniumWrapper] Closed the current window: {}", currentWindowHandle);

            driver.switchTo().window(mainWindowHandle);
            log.debug("[SeleniumWrapper] Switched back to the main window: {}", mainWindowHandle);
        } catch (Exception e) {
            log.error("[SeleniumWrapper] Error occurred while closing the window", e);
            throw e;
        }
    }


    /**
     * Hover on a web element mouse action
     * @param element WebElement
     */
    public static void hoverOnElement(WebElement element) {
        Actions actions = new Actions(WebDriverFactory.getWebDriver());
        actions.moveToElement(element).perform();
    }



    public static void openUrl(String url){
        WebDriver driver = WebDriverFactory.getWebDriver();
        driver.get(url);
    }

}
