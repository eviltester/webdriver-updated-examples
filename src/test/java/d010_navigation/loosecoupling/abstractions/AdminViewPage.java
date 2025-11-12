package d010_navigation.loosecoupling.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminViewPage {
    private final WebDriver driver;

    public AdminViewPage(final WebDriver driver) {
        this.driver = driver;
    }

    public String getHeadingText() {
        return driver.findElement(By.id("adminh")).getText();
    }
}
