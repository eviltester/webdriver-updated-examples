package a005_basic_webdriver_and_junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicSyncTest {

    // Synchronization is one of the most
    // important concepts to understand for robust execution
    // fortunately it is pretty simple we we use ExpectedConditions.

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // start browser before each test is slower than doing it once per test
        // but means we don't have to do as much clean up after the test
        driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/webdriver-example-page");
    }

    private void showAsParaWithInputText(final String inputText) {
        WebElement inputField = driver.findElement(By.id("numentry"));
        inputField.sendKeys(inputText);

        // This updates the DOM, so we should synchronize
        // i.e. wait till the update is finished
        WebElement showInPara = driver.findElement(By.id("show-as-para"));
        showInPara.click();
    }

    @Test
    public void noSynchronizationRequiredForPageLoad(){

        // No synchronization is required for any elements
        // that are ready at page load. WebDriver automatically
        // waits for the page load.

        driver.get("https://testpages.herokuapp.com/styled/webdriver-example-page?number-entry=1234");
        WebElement elem = driver.findElement(By.id("message"));
        assertEquals("one, two, three, four", elem.getText());

        driver.findElement(By.id("clickable-link")).click();

        WebElement elemAfterPageLoad = driver.findElement(By.id("message"));
        assertEquals("one, two, three, four, five, six, seven, eight, nine",
                elemAfterPageLoad.getText());
    }

    @Test
    public void webDriverWaitUsingExpectedConditions(){

        showAsParaWithInputText("123456");

        // ExpectedConditions is a non-core support utility class
        // explore it to find useful out of the box sync methods

        // Try it - explore the range of expected conditions we could use
        // textMatches() - to match by regex
        // attributeContains() - to wait till the data-val attribute is set
        // textToBePresentInElementLocated() - to wait for partial text e.g. two, three, four
        // etc.
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(
                ExpectedConditions.
                    visibilityOfElementLocated(By.id("message")));

        WebElement elem = driver.findElement(By.id("message"));
        assertEquals("one, two, three, four, five, six", elem.getText());
    }

    @Test
    public void waitsWhichIncludeEnoughChecksCanReplaceAsserts(){

        // Try it: Change the number below and the test should fail
        showAsParaWithInputText("123");

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(
                ExpectedConditions.textToBe(By.id("message"),
                        "one, two, three"
                )
        );
    }

    @Test
    public void waitsAndReturnAsALocatorStrategy(){

        showAsParaWithInputText("12345");

        // wait for an element to be in the right state, rather than finding it
        WebElement elem = new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.
                                visibilityOfElementLocated(By.id("message")));

        assertEquals("one, two, three, four, five", elem.getText());
    }

    @Test
    public void reuseAWaitDefinition(){

        showAsParaWithInputText("013454");

        // rather than instantiating a wait all the time,
        // create one once and then re-use it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.id("message")));

        wait.until(ExpectedConditions.
                attributeContains(By.id("message"), "data-vals", "345"));

        WebElement elem = driver.findElement(By.id("message"));
        assertEquals("013454", elem.getAttribute("data-vals"));

    }

    @Test
    public void fluentWaitIsAGenericWaitingAbstraction() {

        // WebDriverWait is built on FluentWait which is a
        // generic abstraction. We can wait for anything.

        final Instant initialTime = Clock.systemDefaultZone().instant();

        // wait 3 seconds
        new FluentWait<>(Clock.systemDefaultZone()).
            withTimeout(Duration.ofSeconds(5)).
                pollingEvery(Duration.ofMillis(100)).
                    until(
                        clock ->
                            clock.instant().isAfter(initialTime.plusSeconds(3))
        );

        final Instant afterWaitingTime = Clock.systemDefaultZone().instant();
        final Long diff = Duration.between(initialTime, afterWaitingTime).getSeconds();

        assertEquals(3L, diff);

        // More generally we would use a library like Awaitility to do this
        // http://www.awaitility.org/
    }

    @AfterEach
    public void closeBrowser(){
        // remember to close the browser
        driver.close();
    }
}
