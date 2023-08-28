package a020_refactoring_to_abstractions;

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

public class CodeWithAbstractionsTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
    }

    @Test
    public void aTestWithLocalMethodReadableAbstractions(){

        driver.get("https://testpages.eviltester.com/styled/apps/notes/simplenotes.html");

        String myNoteTitle = "My First Note";
        String myNoteDetails = "My Actual Note\nspans multiple lines";

        enterNoteTitle(myNoteTitle);
        enterNoteDetails(myNoteDetails);
        clickAddNote();

        final List<WebElement> listItems = getNoteTitles();

        Optional<WebElement> foundElem = listItems.stream().filter(
                            elem -> elem.getText().equals(myNoteTitle)).findFirst();

        assertTrue(foundElem.isPresent(),
                "Could not find note in the list");

    }

    private List<WebElement> getNoteTitles() {
        return new WebDriverWait(
                                            driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("div.note-in-list > p"), 1
                )
        );
    }

    private void clickAddNote() {
        final WebElement addNoteButton =
                driver.findElement(
                        By.cssSelector("button#add-note"));

        addNoteButton.click();
    }

    private void enterNoteDetails(final String myNoteDetails) {
        final WebElement noteDetailsInput =
                driver.findElement(
                        By.cssSelector("#note-details-input"));
        noteDetailsInput.sendKeys(myNoteDetails);
    }

    private void enterNoteTitle(final String myNoteTitle) {
        final WebElement noteTitleInput =
                driver.findElement(
                        By.cssSelector("#note-title-input"));
        noteTitleInput.sendKeys(myNoteTitle);
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
