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

    public String getH1Text() {
        return driver.findElement(By.id("loginh1")).getText();
    }

    public String getLoginErrorMessage() {
        return driver.findElement(By.cssSelector(".loginmessage")).getText();
    }
}
