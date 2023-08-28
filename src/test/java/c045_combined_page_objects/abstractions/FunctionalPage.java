package c045_combined_page_objects.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/*
    The functional page is a natural place to add synchronisation
    because a function might need to 'wait' to be complete.
 */
public class FunctionalPage {

    public static final String CANCELLED_EDIT = "Cancelled Edit";
    public static final String ADDED_NOTE = "Added Note";
    public static final String SHOWN_NOTES = "Notes Shown";
    public static final String DELETED_NOTES = "Deleted All Notes";

    private final WebDriver driver;
    private final StructuralPage structure;

    public FunctionalPage(final WebDriver driver) {
        this.driver = driver;
        this.structure = new StructuralPage(driver);
    }

    public void useLocalStorage() {
        structure.clickLocalStorage();
        structure.clickLoadFromStorage();
    }

    public void useSessionStorage() {
        structure.clickSessionStorage();
        structure.clickLoadFromStorage();
    }

    public void waitForState(String aState){
        structure.waitTillStateDisplays(aState);
    }

    public void addNewNote(final String title, final String noteDetails) {
        structure.enterNoteTitle(title);
        structure.enterNoteDescription(noteDetails);
        structure.clickAddButton();
        waitForState(ADDED_NOTE);
    }

    public void showStoredNotes(){
        structure.clickShowNotesButton();
        waitForState(SHOWN_NOTES);
    }

    public void clearStoredNotes(){
        structure.clickClearButton();
        driver.switchTo().alert().accept();
        waitForState(DELETED_NOTES);
    }

    public List<String> getDisplayedNoteTitles(){

        List<WebElement> notes = structure.getDisplayedNoteElements();

        List<String> noteTitles = new ArrayList<>();

        notes.forEach( shownNote -> {
            final String title = shownNote.findElement(By.tagName("p")).
                    getText();

            noteTitles.add(title);
        });

        return noteTitles;
    }


}
