package d010_navigation.tightcoupling.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminLoginPage {

    private final WebDriver driver;

    public AdminLoginPage(final WebDriver driver) {
        this.driver = driver;
    }

    public LoggedInPage login(String username, String password){

        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login")).click();

        //pauseUntilLoggedIn();
        return new LoggedInPage(driver);
    }

    private void pauseUntilLoggedIn() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                    ExpectedConditions.not(
                            ExpectedConditions.elementToBeClickable(By.id("login"))
                    ));
        }catch(TimeoutException e){
            // do not fail if we do not login

        }
    }

    public SuperAdminLoggedInPage loginAsSuperAdmin(
            final String superAdmin, final String adminPass) {
        login(superAdmin, adminPass);
        return new SuperAdminLoggedInPage(driver);
    }

    public LoggedInPage loginAsAdmin(final String admin, final String adminPass) {
        return login(admin, adminPass);
    }

    public void failToLogin(final String admin, final String password) {
        login(admin, password);
    }

    public String getHeadingText() {
        return driver.findElement(By.id("loginh")).getText();
    }

    public String getLoginErrorMessage() {
        return driver.findElement(By.cssSelector(".loginmessage")).getText();
    }
}
