package a005_basic_webdriver_and_junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasicWebDriverLocatorsTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // start browser before each test is slower than doing it once per test
        // but means we don't have to do as much clean up after the test
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(BasicConstants.TEST_DOMAIN + "/pages/basics/locator-approaches/");
    }


    @Test
    public void findingElementsOnThePage() {

        // find from the page level with driver
        WebElement mainDetails = driver.findElement(By.id("items-to-find"));

        // we can find an element and then find child elements
        WebElement list = mainDetails.findElement(By.tagName("ul"));

        WebElement listItem = list.findElement(By.tagName("li"));

        // when multiple elements match the locator the first is returned
        assertEquals("tagName li", listItem.getText());

        // we can get all the elements that match in a List
        List<WebElement> paragraphs = list.findElements(By.tagName("li"));
        assertEquals("tagName li",
                paragraphs.get(0).getText());
        assertEquals("id li",
                paragraphs.get(1).getText());
    }

    @Test
    public void locatorStrategiesUsingBy() {

        // we use By. items to implement different locator strategies

        // By.id()
        WebElement byId = driver.findElement(By.id("find-by-id"));
        assertEquals("id li", byId.getText());

        // By.className()
        WebElement byClass = driver.findElement(By.className("find-by-class-name"));
        assertEquals("className li", byClass.getText());

        // By.linkText()
        WebElement linkByText = driver.findElement(By.linkText("full link text link"));
        // href is the full domain+path+hashvalue
        assertTrue(linkByText.getAttribute("href").endsWith("#approaches"));

        // By.name()
        WebElement numberbutton = driver.findElement(By.name("find-by-name"));
        assertEquals("name button", numberbutton.getText());

        // By.partialLinkText()
        WebElement partLinkText = driver.findElement(By.partialLinkText("partial link"));
        assertEquals("partial link text link", partLinkText.getText());

        // By.tagName()
        WebElement mainDetails = driver.findElement(By.id("items-to-find"));
        WebElement tagElem = mainDetails.findElement(By.tagName("li"));
        assertEquals("tagName li", tagElem.getText());

        // Generic and most flexible locator methods
        // By.cssSelector()
        WebElement liByCss = driver.findElement(By.cssSelector("li.by-css"));
        assertEquals("by css li", liByCss.getText());

        // learn CSS Selectors to improve your ability to locate elements
        // https://www.w3.org/TR/CSS21/selector.html
        // https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_selectors
        // https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Selectors
        // https://www.w3schools.com/cssref/css_selectors.php

        // By.xpath()
        WebElement paraByXpathId = driver.findElement(By.xpath("//*[@id='child-of-li']"));
        assertEquals("by xpath", paraByXpathId.getText());

        // xpath can be useful, prefer CSS as it is
        // more widely understood by web developers
        // and more consistently implemented by browsers
        // https://developer.mozilla.org/en-US/docs/Web/XPath
        // https://www.w3schools.com/xml/xpath_syntax.asp

        // Note: although we can use the static classes directly
        // don't. Instead, use the factory methods above
        WebElement byBy = driver.findElement(new By.ById("find-by-id"));
        assertEquals("id li", byBy.getText());
    }


    @AfterEach
    public void closeBrowser(){
        // remember to close the browser
        driver.close();
        driver.quit();
    }
}
