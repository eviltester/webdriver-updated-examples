package d010_navigation.tightcoupling;

/*

One example of tightly coupled page objects are when
and action on one page object returns another page object.

 */

import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import d010_navigation.tightcoupling.abstractions.AdminLoginPage;
import d010_navigation.tightcoupling.abstractions.LoggedInPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TightlyCoupledPageObjectsTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.get(new SiteUrls(new Environment()).adminLoginExample());
    }

    @Test
    public void canLoginToAccessNextPage(){
        final AdminLoginPage loginPage = new AdminLoginPage(driver);
        final LoggedInPage adminPage =
                loginPage.login("Admin", "AdminPass");

        assertEquals("Admin View", adminPage.getH1Text());
    }

    @Test
    public void invalidLoginCouldJustIgnoreReturnedLoggedInPage(){
        final AdminLoginPage loginPage = new AdminLoginPage(driver);
        loginPage.login("Admin", "Admin");

        assertEquals("Cookie Controlled Admin",
                driver.findElement(By.tagName("h1")).getText());
    }

    @Test
    public void whatAboutDifferentReturnedLoggedInPages(){
        final AdminLoginPage loginPage = new AdminLoginPage(driver);
        final LoggedInPage adminPage =
                loginPage.login("SuperAdmin", "AdminPass");

        assertThrows(
                NoSuchElementException.class,
                () -> assertEquals("Admin View", adminPage.getH1Text()),
                "We should have logged in to the SuperAdminView Page");

        // we are actually on the super admin page so the
        // returned page is invalid and the tight coupling did not help
        assertEquals("Super Admin View",
                driver.findElement(By.tagName("h1")).getText());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
