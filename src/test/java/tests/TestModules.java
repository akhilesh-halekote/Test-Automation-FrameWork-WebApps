package tests;


import org.qa.common.Config;
import static org.qa.helpers.SeleniumWrapper.*;
import org.qa.pages.LoginPage;


public class TestModules {


    public static void launchPortal() {
        openUrl(Config.URL);
    }

    public static void validLoginScenario() {
        launchPortal();
        LoginPage loginPage = getPage(LoginPage.class);
        loginPage.login(Config.VALID_USER_NAME, Config.VALID_PASSWORD);
    }

}
