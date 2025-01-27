package org.qa.helpers;

import org.openqa.selenium.*;


public class SeleniumWrapper {

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

}
