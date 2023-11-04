package d020_data_objects;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import c040_page_objects.functional.FunctionalNoteTakerPage;
import d020_data_objects.abstractions.NoteEntry;
import d020_data_objects.abstractions.NoteTakerDataPage;
import io.cucumber.java.mk_latn.No;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionalPageObjectTest {

    /*
        a Data Object Page Object

        Is a class that represents the page using the
        higher level Data Objects.

     */
    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(new SiteUrls(new Environment()).simpleNotesApp());
    }

    @Test
    public void canCreateANewNote(){

        NoteTakerDataPage noteTaker = new NoteTakerDataPage(driver);

        noteTaker.useLocalStorage();

        final NoteEntry note1 = NoteEntry.createRandomNoteEntry();

        noteTaker.addNewNote(note1);
        noteTaker.waitForState(FunctionalNoteTakerPage.ADDED_NOTE);
        noteTaker.cancelEdit();

        final NoteEntry note2 = new NoteEntry("controlled note",
                "Details of the controlled note");
        noteTaker.addNewNote(note2);
        noteTaker.waitForState(FunctionalNoteTakerPage.ADDED_NOTE);

        final List<String> shownNotes = noteTaker.getDisplayedNoteTitles();

        assertEquals(note1.getTitle(), shownNotes.get(0));
        assertEquals(note2.getTitle(), shownNotes.get(1));
    }

    @Test
    public void canDeleteNotes(){

        NoteTakerDataPage noteTaker = new NoteTakerDataPage(driver);

        noteTaker.useLocalStorage();

        noteTaker.addNewNote(NoteEntry.createRandomNoteEntry());
        noteTaker.addNewNote(NoteEntry.createRandomNoteEntry());
        noteTaker.showStoredNotes();
        noteTaker.waitForState(FunctionalNoteTakerPage.SHOWN_NOTES);

        noteTaker.clearStoredNotes();

        noteTaker.waitForState(FunctionalNoteTakerPage.DELETED_NOTES);

        noteTaker.showStoredNotes();
        noteTaker.waitForState(FunctionalNoteTakerPage.SHOWN_NOTES);

        final List<String> shownNotes = noteTaker.getDisplayedNoteTitles();

        assertEquals(0, shownNotes.size());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
