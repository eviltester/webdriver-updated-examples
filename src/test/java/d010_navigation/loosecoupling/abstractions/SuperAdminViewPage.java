package d010_navigation.loosecoupling.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuperAdminViewPage {
    private final WebDriver driver;

    public SuperAdminViewPage(final WebDriver driver) {
        this.driver = driver;
    }

    public String getH1Text() {
        return driver.findElement(By.id("superadminh1")).getText();
    }
}
