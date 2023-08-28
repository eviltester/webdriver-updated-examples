package b030_webdriver_provided_abstractions;

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
        driver = new ChromeDriver();
        driver.get(new SiteUrls(new Environment()).examplePage());
    }

    @Test
    public void basicDOMAbstractions(){

        // find elements on the page using By locators
        List<WebElement> elems = driver.findElements(By.className("example-list-item"));

        assertEquals(3, elems.size(), "There should be 3 list items");

        // check list item details
        assertEquals("List Item 1", elems.get(0).getText());
        assertEquals("li2", elems.get(1).getAttribute("data-locator"));
        assertEquals("List Item 3", elems.get(2).getText());
    }


    @AfterEach
    public void closeBrowser(){
        driver.close();
    }

}
