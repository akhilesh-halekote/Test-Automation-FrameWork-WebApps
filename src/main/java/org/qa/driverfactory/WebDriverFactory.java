package org.qa.driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class WebDriverFactory {
    //This is to avoid object creation
    private WebDriverFactory(){}

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**Getter method to return driver
     * @return webDriver
     */
    public static WebDriver getWebDriver(){
        return driver.get();
    }

    /**
     * This method instantiates driver only if it is null
     * @param browser : Type of browser
     */
    public static void setWebDriver(String browser){
        if(driver.get()!=null){
            return;
        }

        if (driver.get() == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);
                    driver.set(chromeDriver);
                    break;
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--start-maximized");
                    firefoxOptions.addArguments("--remote-allow-origins=*");
                    FirefoxDriver firefoxDriver = new FirefoxDriver(firefoxOptions);
                    driver.set(firefoxDriver);
                    break;
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized");
                    edgeOptions.addArguments("--remote-allow-origins=*");
                    EdgeDriver edgeDriver = new EdgeDriver(edgeOptions);
                    driver.set(edgeDriver);
                    break;
                case "safari":
                    SafariOptions safariOptions = new SafariOptions();
                    safariOptions.setCapability("safari:automaticInspection", true);
                    safariOptions.setCapability("safari:automaticProfiling", true);
                    safariOptions.setCapability("safari:diagnose", true);
                    safariOptions.setCapability("safari:useSimulator", true);
                    SafariDriver safariDriver = new SafariDriver(safariOptions);
                    driver.set(safariDriver);
                    safariDriver.manage().window().maximize();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
        }
    }

    public static void quitDriver(){
        if(driver.get()!= null){
            driver.get().quit();
            driver.remove();
        }
    }
}
