package a020_refactoring_to_abstractions;

import a005_basic_webdriver_and_junit.Driver;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
    Highlight code and then
    Use IntelliJ "refactor > extract method"
    to create simple localised abstractions
    and make the test more readable
 */
public class CodeBeforeAbstractionsTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // driver = new ChromeDriver();
        driver = Driver.create();
    }

    @Test
    public void aTestWithNoAbstractions(){

        driver.get("https://testpages.eviltester.com/styled/apps/notes/simplenotes.html");

        String myNoteTitle = "My First Note";

        final WebElement noteTitleInput =
                driver.findElement(
                        By.cssSelector("#note-title-input"));

        noteTitleInput.sendKeys(myNoteTitle);

        String myNoteDetails = "My Actual Note\nspans multiple lines";

        final WebElement noteDetailsInput =
                driver.findElement(
                        By.cssSelector("#note-details-input"));

        noteDetailsInput.sendKeys(myNoteDetails);

        final WebElement addNoteButton =
                driver.findElement(
                        By.cssSelector("button#add-note"));

        addNoteButton.click();

        final List<WebElement> listItems = new WebDriverWait(
                                            driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("div.note-in-list > p"), 1
                )
        );

        Optional<WebElement> foundElem = listItems.stream().filter(
                            elem -> elem.getText().equals(myNoteTitle)).findFirst();

        assertTrue(foundElem.isPresent(),
                "Could not find note in the list");

    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
