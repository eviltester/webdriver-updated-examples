package d010_navigation.tightcoupling.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoggedInPage {
    private final WebDriver driver;

    public LoggedInPage(final WebDriver driver) {
        this.driver = driver;
    }

    public String getHeadingText() {
        return driver.findElement(By.id("adminh")).getText();
    }
}
