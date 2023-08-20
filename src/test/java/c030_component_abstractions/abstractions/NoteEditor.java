package c030_component_abstractions.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NoteEditor {
    private final WebDriver driver;

    public NoteEditor(final WebDriver driver) {
        this.driver = driver;
    }

    public void enterNoteDetails(final String noteTitle,
                                 final String noteDetails) {

        final WebElement title =
                driver.findElement(By.cssSelector("#note-title-input"));
        final WebElement note =
                driver.findElement(By.cssSelector("#note-details-input"));

        title.clear();
        title.sendKeys(noteTitle);

        note.clear();
        note.sendKeys(noteDetails);
    }

    public void addNote() {
        final WebElement addNote =
                driver.findElement(By.cssSelector("#add-note"));
        addNote.click();
    }
}
