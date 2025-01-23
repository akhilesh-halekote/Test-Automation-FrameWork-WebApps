package org.qa.driverfactory;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariOptions;

public class WebDriverFactory {
    //This is to avoid object creation
    private WebDriverFactory(){}

    private static WebDriver webDriver;

    /**Getter method to return driver
     * @return webDriver
     */
    public static WebDriver getWebDriver(){
        return webDriver;
    }

    /**
     * This method instantiates driver only if it is null
     * @param driver : Type of browser
     */
    public static void setWebDriver(String driver){
        if(getWebDriver()!=null){
            return;
        }

        MutableCapabilities options =
                switch(driver) {
                    case "Chrome" -> new ChromeOptions();
                    case "IE" -> new InternetExplorerOptions();
                    case "Safari" -> new SafariOptions();
                    case "FireFox" -> new FirefoxOptions();
                    default -> throw new IllegalStateException("Unexpected value: " + driver);
                };
        options.setCapability("start-maximized", true);
        options.setCapability("window-size","1920,1080");
        options.setCapability("disable-popup-blocking", true);
        options.setCapability("--disable-default-apps", true);
    }

    public static void quitDriver(){
        if(getWebDriver()!= null){
            webDriver.quit();
        }
    }
}
