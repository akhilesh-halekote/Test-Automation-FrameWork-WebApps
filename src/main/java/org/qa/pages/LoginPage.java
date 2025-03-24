package org.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.qa.helpers.SeleniumWrapper;

public class LoginPage {


    @FindBy(id="user-name")
    private WebElement user_name_text_box;

    @FindBy(id="password")
    private WebElement password_text_box;

    @FindBy(id="login-button")
    private WebElement login_button;

    public void login(String username, String password){
        if(!isLoginPageLoaded()){
            throw new RuntimeException();
        }
        SeleniumWrapper.inputText(user_name_text_box, username);
        SeleniumWrapper.inputText(password_text_box, password);
        SeleniumWrapper.clickOnElement(login_button);

    }

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }



    public boolean isLoginPageLoaded(){
        return SeleniumWrapper.isDisplayed(user_name_text_box);
    }


}
