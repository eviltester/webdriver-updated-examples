package c030_component_abstractions;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import c030_component_abstractions.abstractions.NoteEditor;
import c030_component_abstractions.abstractions.NotesList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleNoteComponentsTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(new SiteUrls(new Environment()).simpleNotesApp());
    }

    @Test
    public void canInteractWithWebElement(){

        // check number of notes in the notes list
        List<WebElement> notes = driver.findElements(By.cssSelector("div.note-in-list"));
        int initialNotesTotal = notes.size();


        // enter a note
        final WebElement title = driver.findElement(By.cssSelector("#note-title-input"));
        final WebElement note = driver.findElement(By.cssSelector("#note-details-input"));
        final WebElement addNote = driver.findElement(By.cssSelector("#add-note"));

        title.clear();
        title.sendKeys("My note title");

        note.clear();
        note.sendKeys("My note details");

        addNote.click();

        // check note in the notes list
        notes = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("div.note-in-list"),
                        initialNotesTotal+1));
        WebElement finalNote = notes.get(notes.size()-1);
        WebElement finalNoteTitle = finalNote.findElement(By.className("title-note-in-list"));

        assertEquals("My note title", finalNoteTitle.getText());

    }

    @Test
    public void canInteractWithComponents(){

        NotesList notesList = new NotesList(driver);
        int initialTotal = notesList.count();

        NoteEditor noteEdit = new NoteEditor(driver);
        noteEdit.enterNoteDetails("My note title",
                "My note details");
        noteEdit.addNote();

        new FluentWait<>(notesList).
            withTimeout(Duration.ofSeconds(10)).until(
                (aNote) -> aNote.isNoteVisible("My note title"));

        assertEquals(initialTotal+1, notesList.count());
        assertTrue(notesList.isNoteVisible("My note title"));
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
