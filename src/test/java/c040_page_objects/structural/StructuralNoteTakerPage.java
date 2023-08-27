package c040_page_objects.structural;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StructuralNoteTakerPage {

    public static final String CANCELLED_EDIT = "Cancelled Edit";
    private final WebDriver driver;

    public StructuralNoteTakerPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void clickLocalStorage() {
        final WebElement link = driver.findElement(By.id("local-storage-link"));
        link.click();
    }

    public void clickLoadFromStorage() {
        final WebElement link = driver.findElement(By.id("load-notes"));
        link.click();
    }

    public void enterNoteTitle(final String title) {

        final WebElement titleField =
                driver.findElement(
                        By.cssSelector("#note-title-input"));

        titleField.sendKeys(title);
    }

    public void enterNoteDescription(final String note) {

        final WebElement noteField =
                driver.findElement(
                        By.cssSelector("#note-details-input"));

        noteField.sendKeys(note);
    }

    public void enterNoteDetails(final String title, final String note) {
        enterNoteTitle(title);
        enterNoteDescription(note);
    }

    public void clickAddButton() {
        final WebElement link = driver.findElement(By.id("add-note"));
        link.click();
    }

    public void clickCancelButton() {
        final WebElement link = driver.findElement(By.id("cancel-note"));
        link.click();
    }

    public List<String> getDisplayedNoteTitles(){

        List<WebElement> notes = driver.findElements(By.className("note-in-list"));

        List<String> noteTitles = new ArrayList<>();

        notes.forEach( shownNote -> {
            final String title = shownNote.findElement(By.tagName("p")).
                    getText();

            noteTitles.add(title);
        });

        return noteTitles;
    }


    public void waitTillStateDisplays(final String state) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.textToBe(
                        By.cssSelector("#note-status-details"),
                        state));
    }
}
