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

import static org.junit.jupiter.api.Assertions.*;

public class BasicWebDriverInteractionsTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // start browser before each test is slower than doing it once per test
        // but means we don't have to do as much clean up after the test
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(BasicConstants.TEST_DOMAIN + "/apps/numbers-to-text/");

        // NOTE: it would be even faster if we put Driver.create in a BeforeAll,
        // and just .get(the page) for each test to reset the state of the application
    }

    @Test
    public void interactWithPageUsingWebElement() {

        WebElement inputField = driver.findElement(By.id("numentry"));
        inputField.sendKeys("12345");

        // we might need to wait for sendkeys
        new WebDriverWait(driver, Duration.ofSeconds(10)).
            until(ExpectedConditions.textToBePresentInElementValue(inputField,"12345"));

        WebElement submitButton = driver.findElement(By.id("submit-to-server"));
        submitButton.click();

        // webdriver will automatically wait for page to load after submitting
        // if we 'clicked' the button then we might be finding it before submission
        new WebDriverWait(driver, Duration.ofSeconds(10)).
            until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElementValue(inputField,"")));


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
        driver.quit();
    }
}
