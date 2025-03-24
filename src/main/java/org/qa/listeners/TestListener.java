package org.qa.listeners;
import org.openqa.selenium.WebDriver;
import org.qa.driverfactory.Browser;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.qa.driverfactory.WebDriverFactory;

public class TestListener implements ITestListener {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        if (result.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(Browser.class)) {
            Browser browserAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Browser.class);
            WebDriverFactory.setWebDriver(browserAnnotation.value());
            driverThreadLocal.set(WebDriverFactory.getWebDriver());
            return;
        }
        WebDriverFactory.setWebDriver("chrome");
        driverThreadLocal.set(WebDriverFactory.getWebDriver());

    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @Override
    public void onFinish(ITestContext context) {
        WebDriverFactory.quitDriver();
        driverThreadLocal.remove();
    }

}