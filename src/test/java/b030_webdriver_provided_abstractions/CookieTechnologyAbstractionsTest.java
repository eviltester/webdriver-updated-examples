package b030_webdriver_provided_abstractions;

import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CookieTechnologyAbstractionsTest {

    private WebDriver driver;

    // WebDriver provides an abstraction around the cookies so we can
    // interrogate and manipulate cookies

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.get(new SiteUrls(new Environment()).adminLoginExample());
    }

    @Test
    public void checkCookieCreatedWhenUserLogsIn(){

        // cookie should not exist
        Cookie cookie = driver.manage().getCookieNamed("loggedin");
        assertNull(cookie);

        // Login
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("AdminPass");
        driver.findElement(By.id("login")).click();

        // cookie should now exist
        cookie = driver.manage().getCookieNamed("loggedin");
        assertNotNull(cookie);

        // and we should have redirected to Admin View Page
        new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(
                        ExpectedConditions.
                                elementToBeClickable(By.id("gologin"))
                );
    }

    @Test
    public void bypassLoginProcessAndInjectALoggedInCookie(){

        // cookie should not exist
        Cookie cookie = driver.manage().getCookieNamed("loggedin");
        assertNull(cookie);

        // create it and refresh page
        driver.manage().addCookie(
                new Cookie("loggedin", "Admin", "/")
        );
        driver.navigate().refresh();

        // we should redirect to "Admin View" page
        new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(
                ExpectedConditions.
                        elementToBeClickable(By.id("gologin"))
        );
    }

    @Test
    public void whenLoggedInWeShouldBeAbleToLogout(){
        // fake the login
        driver.manage().addCookie(
                new Cookie("loggedin", "Admin", "/")
        );
        driver.navigate().refresh();

        // logout
        final WebElement logoutLink = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(
                        ExpectedConditions.
                                elementToBeClickable(By.id("navadminlogout"))
                );

        logoutLink.click();

        // wait for page refresh
        final WebElement loginButton = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(
                        ExpectedConditions.
                                elementToBeClickable(By.id("login"))
                );

        // cookie should not exist
        Cookie cookie = driver.manage().getCookieNamed("loggedin");
        assertNull(cookie);

    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
