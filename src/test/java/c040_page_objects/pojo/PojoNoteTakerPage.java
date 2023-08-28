package c040_page_objects.pojo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PojoNoteTakerPage {

    private final WebDriver driver;

    public PojoNoteTakerPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void useLocalStorage() {
        driver.findElement(By.id("local-storage-link")).click();
    }

    public void useSessionStorage() {
        driver.findElement(By.id("session-storage-link")).click();
    }

    public void enterNoteDetails(final String title, final String noteDetails) {

        final WebElement titleField =
                driver.findElement(
                        By.cssSelector("#note-title-input"));

        titleField.sendKeys(title);

        final WebElement noteField =
                driver.findElement(
                        By.cssSelector("#note-details-input"));

        noteField.sendKeys(noteDetails);
    }

    public void clickAddButton(){
        final WebElement addButton = driver.findElement(By.id("add-note"));
        addButton.click();
    }

    public void clickCancelButton(){
        final WebElement cancelButton = driver.findElement(By.id("cancel-note"));
        cancelButton.click();
    }

    public void waitTillHaveCancelledEdit(){
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.textToBe(
                        By.cssSelector("#note-status-details"),
                        "Cancelled Edit"));
    }

    public void addNewNote(final String title, final String noteDetails) {
        enterNoteDetails(title, noteDetails);
        clickAddButton();
    }

    public void loadFromStorage(){
        final WebElement loadButton = driver.findElement(By.id("load-notes"));
        loadButton.click();
    }

    public void clickShowButton(){
        final WebElement showButton = driver.findElement(By.id("show-notes"));
        showButton.click();
    }

    public void clickClearAllButton(){
        final WebElement clearAllButton = driver.findElement(By.id("clear-notes"));
        clearAllButton.click();
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
