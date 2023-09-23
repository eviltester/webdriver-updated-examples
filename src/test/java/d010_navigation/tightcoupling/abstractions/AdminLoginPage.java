package d010_navigation.tightcoupling.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminLoginPage {

    private final WebDriver driver;

    public AdminLoginPage(final WebDriver driver) {
        this.driver = driver;
    }

    public LoggedInPage login(String username, String password){

        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login")).click();
        return new LoggedInPage(driver);
    }
}
