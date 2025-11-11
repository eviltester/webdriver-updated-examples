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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomTechnologyAbstractionsTest {

    private WebDriver driver;

    // The DOM (Document Object Model) is what we think of as the page
    // WebDrivers WebElement, findElement(s) and By are the primary
    // DOM abstractions.

    @BeforeEach
    public void startBrowser(){
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(new SiteUrls(new Environment()).examplePage());
    }

    @Test
    public void basicDOMAbstractions(){

        // find elements on the page using By locators all paras with ids
        List<WebElement> elems = driver.findElements(By.cssSelector("p[id]"));

        assertEquals(3, elems.size(), "There should be 3 paras items");

        // check list item details
        assertEquals("A paragraph of text", elems.get(0).getText());
        assertEquals("Another paragraph of text", elems.get(1).getText());
        assertEquals("click-message", elems.get(2).getAttribute("id"));
    }


    @AfterEach
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }

}
