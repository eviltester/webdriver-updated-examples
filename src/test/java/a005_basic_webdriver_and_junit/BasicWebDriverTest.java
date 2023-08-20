package a005_basic_webdriver_and_junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicWebDriverTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // start browser before each test is slower than doing it once per test
        // but means we don't have to do as much clean up after the test
        driver = new ChromeDriver();
    }

    @Test
    public void aBasicWebDriverTest() {
        // open a URL in the browser
        driver.get("https://testpages.herokuapp.com/styled/basic-web-page-test.html");

        // find elements on the page using By locators
        WebElement p1 = driver.findElement(By.id("para1"));

        // get the text of a web element
        assertEquals("A paragraph of text", p1.getText());

        // we can get the attributes on an element
        assertEquals("main", p1.getAttribute("class"));
        assertEquals("para1", p1.getAttribute("id"));

        // we can find an element and then get child elements
        WebElement mainDetails = driver.findElement(By.className("centered"));

        WebElement firstPara = mainDetails.findElement(By.tagName("p"));

        // when multiple elements match the locator the first is returned
        assertEquals("A paragraph of text", firstPara.getText());

        // we can get all the elements that match in a List
        List<WebElement> paragraphs = mainDetails.findElements(By.tagName("p"));
        assertEquals("A paragraph of text",
                paragraphs.get(0).getText());
        assertEquals("Another paragraph of text",
                paragraphs.get(1).getText());
    }

    @AfterEach
    public void closeBrowser(){
        // remember to close the browser
        driver.close();
    }
}
