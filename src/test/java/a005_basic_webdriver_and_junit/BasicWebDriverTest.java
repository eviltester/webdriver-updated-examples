package a005_basic_webdriver_and_junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasicWebDriverTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // start browser before each test is slower than doing it once per test
        // but means we don't have to do as much clean up after the test
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(BasicConstants.TEST_PAGE_URL);
    }

    @Test
    public void aBasicWebDriverTest() {
        // open a URL in the browser - we often just do this in the @BeforeAll or @BeforeEach
        driver.get(BasicConstants.TEST_PAGE_URL);

        // find elements on the page using By locators
        WebElement p1 = driver.findElement(By.id("para1"));

        // get the text of a web element
        assertEquals("A paragraph of text", p1.getText());
    }

    @Test
    public void driverLevelInterrogation() {

        String pageTitle = driver.getTitle();
        assertEquals("Example Page Title", pageTitle);

        String currentUrl = driver.getCurrentUrl();
        assertEquals(
                BasicConstants.TEST_PAGE_URL,
                currentUrl);

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Example Page Heading One"));

        // explore the additional 'get' methods on driver
    }

    @Test
    public void findingElementsOnThePage() {

        // find from the page level with driver
        WebElement mainDetails = driver.findElement(By.id("main-example-paras"));

        // we can find an element and then find child elements
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

    @Test
    public void locatorStrategiesUsingBy() {

        // we use By. items to implement different locator strategies

        // By.id()
        WebElement paraById = driver.findElement(By.id("para1"));
        assertEquals("A paragraph of text", paraById.getText());

        // By.className()
        WebElement paraByClass = driver.findElement(By.className("main"));
        assertEquals("A paragraph of text", paraByClass.getText());

        // By.linkText()
        WebElement linkByText = driver.findElement(By.linkText("Show From Link"));
        assertEquals("clickable-link", linkByText.getAttribute("id"));

        // By.name()
        WebElement numberentry = driver.findElement(By.name("number-entry"));
        assertEquals("numentry", numberentry.getAttribute("id"));

        // By.partialLinkText()
        WebElement partLinkText = driver.findElement(By.partialLinkText("From Link"));
        assertEquals("clickable-link", partLinkText.getAttribute("id"));

        // By.tagName()
        WebElement tagElem = driver.findElement(By.tagName("li"));
        assertEquals("List Item 1", tagElem.getText());

        // Generic and most flexible locator methods
        // By.cssSelector()
        WebElement paraByCssId = driver.findElement(By.cssSelector("#para1"));
        assertEquals("A paragraph of text", paraByCssId.getText());

        // learn CSS Selectors to improve your ability to locate elements
        // https://www.w3.org/TR/CSS21/selector.html
        // https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_selectors
        // https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Selectors
        // https://www.w3schools.com/cssref/css_selectors.php

        // By.xpath()
        WebElement paraByXpathId = driver.findElement(By.xpath("//*[@id='para1']"));
        assertEquals("A paragraph of text", paraByXpathId.getText());

        // xpath can be useful, prefer CSS as it is
        // more widely understood by web developers
        // and more consistently implemented by browsers
        // https://developer.mozilla.org/en-US/docs/Web/XPath
        // https://www.w3schools.com/xml/xpath_syntax.asp

        // Note: although we can use the static classes directly
        // don't. Instead, use the factory methods above
        WebElement byBy = driver.findElement(new By.ById("para1"));
        assertEquals("A paragraph of text", byBy.getText());
    }

    // interrogation of web elements get attribute etc.
    @Test
    public void interrogationOfWebElements() {

        // explore By. items
        // find elements on the page using By locators
        WebElement p1 = driver.findElement(By.id("para1"));

        // we can get the attributes on an element
        assertEquals("main", p1.getAttribute("class"));
        assertEquals("para1", p1.getAttribute("id"));
        assertEquals("para1", p1.getAttribute("data-locator"));

        // get the text of a web element
        assertEquals("A paragraph of text", p1.getText());

        assertFalse(p1.isSelected(), "can't select a paragraph");
        assertTrue(p1.isDisplayed());
        assertTrue(p1.isEnabled(), "paragraphs are enabled");

        assertEquals("p",p1.getTagName());

        // there are a large number of WebElement 'get' interrogation methods,
        // explore them

    }

        @Test
    public void canNavigateWithMultipleApproaches(){

        // using get
        driver.get(BasicConstants.TEST_DOMAIN + "/styled/basic-web-page-test.html");
        assertEquals("Basic Web Page Example",
                driver.findElement(By.tagName("h1")).getText());

        // or using navigate
        driver.navigate().to(BasicConstants.TEST_DOMAIN + "/styled/webdriver-example-page");
        assertEquals("Example Page Heading One",
                driver.findElement(By.tagName("h1")).getText());

        // we can navigate back again
        driver.navigate().back();
        assertEquals("Basic Web Page Example",
                driver.findElement(By.tagName("h1")).getText());

        // and navigate forward
        driver.navigate().forward();
        assertEquals("Example Page Heading One",
                driver.findElement(By.tagName("h1")).getText());
    }

    @Test
    public void interactWithPageUsingWebElement() {

        WebElement inputField = driver.findElement(By.id("numentry"));
        inputField.sendKeys("12345");

        WebElement submitButton = driver.findElement(By.id("submit-to-server"));
        submitButton.click();

        // webdriver will automatically wait for page to load after submitting
        WebElement message = driver.findElement(By.id("message"));
        assertEquals("one, two, three, four, five", message.getText());
    }

    @Test
    public void clickALinkAndWebDriverWillAutomaticallyWait() {

        WebElement link = driver.findElement(By.id("clickable-link"));
        link.click();

        // webdriver will automatically wait for page to load after submitting
        WebElement message = driver.findElement(By.id("message"));
        assertEquals(
            "one, two, three, four, five, six, seven, eight, nine",
            message.getText());
    }

    @Test
    public void webdriverCanHandleAlerts() {

        WebElement inputField = driver.findElement(By.id("numentry"));
        inputField.sendKeys("12345");

        WebElement showAlertButton = driver.findElement(By.id("show-as-alert"));
        showAlertButton.click();

        String alertText = driver.switchTo().alert().getText();
        assertEquals("one, two, three, four, five", alertText);

        driver.switchTo().alert().dismiss();
    }

    @Test
    public void withDynamicPageUpdatesWeShouldWait() {

        WebElement inputField = driver.findElement(By.id("numentry"));
        inputField.sendKeys("123456");

        WebElement showInPara = driver.findElement(By.id("show-as-para"));
        showInPara.click();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.textToBe(By.id("message"),
                "one, two, three, four, five, six"
                )
        );

        // at this point the paragraph has the text and we do not need to assert
        // but we can...
        WebElement message = driver.findElement(By.id("message"));
        assertEquals("one, two, three, four, five, six", message.getText());
    }

    @AfterEach
    public void closeBrowser(){
        // remember to close the browser
        driver.close();
    }
}
