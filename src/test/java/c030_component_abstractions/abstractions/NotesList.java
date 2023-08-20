package c030_component_abstractions.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

public class NotesList {
    private final WebDriver driver;

    public NotesList(final WebDriver driver) {
        this.driver = driver;
    }

    public int count() {
        List<WebElement> notes = driver.findElements(By.cssSelector("div.note-in-list"));
        return notes.size();
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
