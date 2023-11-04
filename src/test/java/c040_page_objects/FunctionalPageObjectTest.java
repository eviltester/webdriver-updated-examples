package c040_page_objects;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import c040_page_objects.functional.FunctionalNoteTakerPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionalPageObjectTest {

    /*
        a Functional Page Object

        Is a class that represents the page in terms of the functionality
        it exposes.

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

        FunctionalNoteTakerPage noteTaker = new FunctionalNoteTakerPage(driver);

        noteTaker.useLocalStorage();

        noteTaker.addNewNote("my first title", "my note details");
        noteTaker.waitForState(FunctionalNoteTakerPage.ADDED_NOTE);
        noteTaker.cancelEdit();

        noteTaker.addNewNote("my second title", "my second note details");
        noteTaker.waitForState(FunctionalNoteTakerPage.ADDED_NOTE);

        final List<String> shownNotes = noteTaker.getDisplayedNoteTitles();

        assertEquals("my first title", shownNotes.get(0));
        assertEquals("my second title", shownNotes.get(1));
    }

    @Test
    public void canDeleteNotes(){

        FunctionalNoteTakerPage noteTaker = new FunctionalNoteTakerPage(driver);

        noteTaker.useLocalStorage();

        noteTaker.addNewNote("my first title", "my note details");
        noteTaker.addNewNote("my second title", "my second note details");
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
        driver.quit();
    }
}
