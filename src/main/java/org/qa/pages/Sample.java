package org.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Sample {

    public Sample(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "class-name")
    public WebElement webElement1;

    @FindBy(id = "id")
    public WebElement webElement2;

    @FindBy(xpath = "//xpath")
    public WebElement webElement3;

    @FindBy(linkText = "link-text")
    public WebElement webElement4;

    @FindBy(css = "css")
    public WebElement webElement5;

    /**
     * This performs click operation on webElement
     */
    public void enterFormDetails(){
        webElement1.clear();
        webElement1.sendKeys("Value1");
        webElement2.clear();
        webElement2.sendKeys("Value2");
        webElement3.clear();
        webElement3.sendKeys("Value3");
        webElement4.clear();
        webElement4.sendKeys("Value4");
        webElement5.click();
    }
}
