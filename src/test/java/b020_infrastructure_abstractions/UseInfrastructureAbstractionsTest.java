package b020_infrastructure_abstractions;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UseInfrastructureAbstractionsTest {

    private WebDriver driver;

    @BeforeEach
    public void setupData(){
        driver = Driver.create();
    }

    @Test
    public void adminLoginButtonHasExpectedText(){

        SiteUrls site = new SiteUrls(new Environment());
        driver.get(site.adminLoginExample());

        WebElement loginButton = driver.findElement(By.id("login"));
        assertEquals(loginButton.getText(), "Login");
    }


    @AfterEach
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
