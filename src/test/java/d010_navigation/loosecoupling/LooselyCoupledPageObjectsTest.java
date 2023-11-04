package d010_navigation.loosecoupling;

/*

We know the flow we expect in the test, keep that responsibility
in the @Test, don't delegate it to a Page Object.

 */

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import d010_navigation.loosecoupling.abstractions.AdminViewPage;
import d010_navigation.loosecoupling.abstractions.CookieManagedNav;
import d010_navigation.loosecoupling.abstractions.SuperAdminViewPage;
import d010_navigation.tightcoupling.abstractions.AdminLoginPage;
import d010_navigation.tightcoupling.abstractions.LoggedInPage;
import d010_navigation.tightcoupling.abstractions.SuperAdminLoggedInPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LooselyCoupledPageObjectsTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(new SiteUrls(new Environment()).adminLoginExample());
    }

    @Test
    public void canLoginAsAdmin(){
        final AdminLoginPage loginPage = new AdminLoginPage(driver);
        loginPage.login("Admin", "AdminPass");

        final AdminViewPage adminView = new AdminViewPage(driver);
        assertEquals("Admin View", adminView.getH1Text());
    }

    @Test
    public void invalidLoginNotAllowed(){
        final AdminLoginPage loginPage = new AdminLoginPage(driver);
        loginPage.login("Admin", "Admin");

        assertEquals("Cookie Controlled Admin", loginPage.getH1Text());

        assertEquals("Login Details Incorrect",
                loginPage.getLoginErrorMessage());

    }

    @Test
    public void canLoginAsSuperAdmin(){
        final AdminLoginPage loginPage = new AdminLoginPage(driver);
        loginPage.login("SuperAdmin", "AdminPass");

        final SuperAdminViewPage loggedInPage = new SuperAdminViewPage(driver);
        assertEquals("Super Admin View", loggedInPage.getH1Text());
    }

    // common Navigation Actions Can Be Elevated and delegated to another class
    @Test
    public void canLoginAndLogout(){

        final AdminLoginPage loginPage = new AdminLoginPage(driver);

        // cannot logout initially
        final CookieManagedNav nav = new CookieManagedNav(driver);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(
                ExpectedConditions.attributeToBe(
                        CookieManagedNav.logoutLink,"href", "")
        );

        assertFalse(nav.isLogoutEnabled());

        // After logging in
        loginPage.login("SuperAdmin", "AdminPass");

        // we can log out
        final SuperAdminViewPage loggedInPage = new SuperAdminViewPage(driver);
        assertEquals("Super Admin View", loggedInPage.getH1Text());

        assertTrue(nav.isLogoutEnabled());

        nav.logout();
        assertEquals("Cookie Controlled Admin", loginPage.getH1Text());
    }

    // could create a navigation class
    @Test
    public void loginPageIsAccessibleWhenNotLoggedIn(){
        final CookieManagedDirectNav jumpTo = new CookieManagedDirectNav(driver);

        jumpTo.loginPage();

        final AdminLoginPage loginPage = new AdminLoginPage(driver);
        assertEquals("Cookie Controlled Admin", loginPage.getH1Text());
    }

    @Test
    public void loginPageIsNotAccessibleWhenLoggedIn(){
        final CookieManagedDirectNav jumpTo = new CookieManagedDirectNav(driver);

        jumpTo.loginPage();

        final AdminLoginPage loginPage = new AdminLoginPage(driver);
        loginPage.login("SuperAdmin", "AdminPass");

        final SuperAdminViewPage loggedInPage = new SuperAdminViewPage(driver);
        assertEquals("Super Admin View", loggedInPage.getH1Text());

        jumpTo.loginPage(); // will redirect back to super admin view page

        assertEquals("Super Admin View", loggedInPage.getH1Text());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
