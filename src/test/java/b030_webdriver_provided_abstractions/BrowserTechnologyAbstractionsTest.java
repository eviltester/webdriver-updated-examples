package b030_webdriver_provided_abstractions;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrowserTechnologyAbstractionsTest {

    // WebDriver is a generic Browser Interface
    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // We instantiate specific browser instances
        // by coding to the Generic Abstraction we don't have
        // to change our @Test code when we use a different browser
        //      try switching between browsers

        //driver = new FirefoxDriver();
        // driver = new ChromeDriver();
        driver = Driver.create();
    }

    @Test
    public void navigate(){

        driver.get(new SiteUrls(new Environment()).examplePage());

       WebElement heading = driver.findElement(By.tagName("h1"));
       assertEquals( "Example Page Heading One", heading.getText());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
