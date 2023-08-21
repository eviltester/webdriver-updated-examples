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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        driver.get("https://testpages.herokuapp.com/styled/webdriver-example-page");

        // find elements on the page using By locators
        WebElement p1 = driver.findElement(By.id("para1"));

        // get the text of a web element
        assertEquals("A paragraph of text", p1.getText());

        // we can get the attributes on an element
        assertEquals("main", p1.getAttribute("class"));
        assertEquals("para1", p1.getAttribute("id"));
        assertEquals("para1", p1.getAttribute("data-locator"));

        // we can find an element and then get child elements
        WebElement mainDetails = driver.findElement(By.id("main-example-paras"));

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
    public void driverLevelInterrogation() {
        driver.get("https://testpages.herokuapp.com/styled/webdriver-example-page");

        String pageTitle = driver.getTitle();
        assertEquals("Example Page Title", pageTitle);

        String currentUrl = driver.getCurrentUrl();
        assertEquals(
            "https://testpages.herokuapp.com/styled/webdriver-example-page",
                currentUrl);


        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Example Page Heading One"));
    }

    @Test
    public void canNavigateWithMultipleApproaches(){

        // using get
        driver.get("https://testpages.herokuapp.com/styled/basic-web-page-test.html");
        assertEquals("Basic Web Page Example",
                driver.findElement(By.tagName("h1")).getText());

        // or using navigate
        driver.navigate().to("https://testpages.herokuapp.com/styled/webdriver-example-page");
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

        driver.get("https://testpages.herokuapp.com/styled/webdriver-example-page");

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

        driver.get("https://testpages.herokuapp.com/styled/webdriver-example-page");

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

        driver.get("https://testpages.herokuapp.com/styled/webdriver-example-page");

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

        driver.get("https://testpages.herokuapp.com/styled/webdriver-example-page");

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
