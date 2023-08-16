package a010_how_abstractions_help.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

public class NotesPage {
    public static final By NOTES_LIST_ITEM = By.cssSelector("p.title-note-in-list");
    private final WebDriver driver;

    public NotesPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void enterNote(final String noteTitle, final String noteContent) {
        final WebElement titleField =
                driver.findElement(
                        By.cssSelector("#note-title-input"));

        titleField.sendKeys(noteTitle);

        final WebElement noteField =
                driver.findElement(
                        By.cssSelector("#note-details-input"));

        noteField.sendKeys(noteContent);

        final WebElement addButton = driver.findElement(By.id("add-note"));
        addButton.click();

    }

    public boolean isNoteVisible(final String noteTitle) {
        List<WebElement> notes = driver.findElements(
                By.cssSelector(
                        "p.title-note-in-list"));

        Optional<WebElement> foundNote = notes.stream().
            filter(aNote -> aNote.getText().trim().equals(noteTitle)).
            findFirst();

        return foundNote.isPresent();
    }

}
