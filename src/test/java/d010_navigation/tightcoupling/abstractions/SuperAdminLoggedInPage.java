package d010_navigation.tightcoupling.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuperAdminLoggedInPage {
    private final WebDriver driver;

    public SuperAdminLoggedInPage(final WebDriver driver) {
        this.driver = driver;
    }

    public String getH1Text() {
        return driver.findElement(By.id("superadminh1")).getText();
    }
}
