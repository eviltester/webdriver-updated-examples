package e010_dsl.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

public class SimpleNotesAppPage {
    public static final By NOTES_LIST_ITEM = By.cssSelector("p.title-note-in-list");
    private final WebDriver driver;

    public SimpleNotesAppPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void enterNote(SimpleNotesAppEntry aNote) {
        final WebElement titleField =
                driver.findElement(
                        By.cssSelector("#note-title-input"));

        titleField.sendKeys(aNote.getTitle());

        final WebElement noteField =
                driver.findElement(
                        By.cssSelector("#note-details-input"));

        noteField.sendKeys(aNote.getNote());

        final WebElement addButton = driver.findElement(By.id("add-note"));
        addButton.click();

    }

    public boolean isNoteVisible(final String noteTitle) {
        List<WebElement> notes = driver.findElements(NOTES_LIST_ITEM);

        Optional<WebElement> foundNote = notes.stream().
            filter(aNote -> aNote.getText().trim().equals(noteTitle)).
            findFirst();

        return foundNote.isPresent();
    }

    public void useLocalStorage() {
        driver.findElement(By.id("local-storage-link")).click();
        loadFromStorage();
    }

    private void loadFromStorage(){
        final WebElement loadButton = driver.findElement(By.id("load-notes"));
        loadButton.click();
    }
}
