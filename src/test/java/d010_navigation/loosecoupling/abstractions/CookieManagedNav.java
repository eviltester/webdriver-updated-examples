package d010_navigation.loosecoupling.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CookieManagedNav {
    private final WebDriver driver;

    public static final By logoutLink = By.id("navadminlogout");

    public CookieManagedNav(final WebDriver driver) {
        this.driver = driver;
    }

    public void logout() {
        driver.findElement(logoutLink).click();
    }

    public boolean isLogoutEnabled(){
        WebElement link = driver.findElement(logoutLink);
        return  link.getAttribute("href") != null &&
                link.getAttribute("href") != "";
    }
}
