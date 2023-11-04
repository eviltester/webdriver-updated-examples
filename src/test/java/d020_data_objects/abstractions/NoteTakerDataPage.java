package d020_data_objects.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class NoteTakerDataPage {

    public static final String CANCELLED_EDIT = "Cancelled Edit";
    public static final String ADDED_NOTE = "Added Note";
    public static final String SHOWN_NOTES = "Notes Shown";
    public static final String DELETED_NOTES = "Deleted All Notes";

    private final WebDriver driver;

    public NoteTakerDataPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void useLocalStorage() {
        driver.findElement(By.id("local-storage-link")).click();
        loadFromStorage();
    }

    public void useSessionStorage() {
        driver.findElement(By.id("session-storage-link")).click();
        loadFromStorage();
    }

    public void enterNoteDetails(NoteEntry aNote) {

        final WebElement titleField =
                driver.findElement(
                        By.cssSelector("#note-title-input"));

        titleField.sendKeys(aNote.getTitle());

        final WebElement noteField =
                driver.findElement(
                        By.cssSelector("#note-details-input"));

        noteField.sendKeys(aNote.getNote());
    }

    public void addNoteDetails(){
        final WebElement addButton = driver.findElement(By.id("add-note"));
        addButton.click();
    }


    public void cancelEdit() {
        final WebElement cancelButton = driver.findElement(By.id("cancel-note"));
        cancelButton.click();
    }

    public void waitForState(String aState){
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.textToBe(
                        By.cssSelector("#note-status-details"),
                        aState));
    }

    public void addNewNote(NoteEntry aNote) {
        enterNoteDetails(aNote);
        addNoteDetails();
    }


    public void loadFromStorage(){
        final WebElement loadButton = driver.findElement(By.id("load-notes"));
        loadButton.click();
    }

    public void showStoredNotes(){
        final WebElement showButton = driver.findElement(By.id("show-notes"));
        showButton.click();
    }

    public void clearStoredNotes(){
        final WebElement clearAllButton = driver.findElement(By.id("clear-notes"));
        clearAllButton.click();

        driver.switchTo().alert().accept();
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


}
