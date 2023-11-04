package c045_combined_page_objects;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import c040_page_objects.functional.FunctionalNoteTakerPage;
import c045_combined_page_objects.abstractions.FunctionalPage;
import c045_combined_page_objects.abstractions.StructuralPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CombiningPageObjectsTest {

    /*
        The benefit from 'types' of page objects is that it defines
        limits of what I would add into a page object.

        e.g. synchronisation in methods on pojo or functional, but not in
        structural. Structural might have sync methods but they would be
        standalone.

        Structural might have combined methods e.g. combine enterTitle and enterDetails
        into a enterNoteDetails method, but this isn't really necessary.

        And when I combine Page Objects i.e. the Locator object is used by the
        Structural object, which is used by the Functional Page object then the
        different abstraction levels often become cleaner and smaller.

        Higher level abstractions like Functional and Pojo, don't have to try and cover ever
        functional scenario because we can drop down to lower abstraction levels
        when implementing execution code.
     */

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(new SiteUrls(new Environment()).simpleNotesApp());
    }

    @Test
    public void canCreateNewNotes(){

        FunctionalPage noteTaker = new FunctionalPage(driver);

        noteTaker.useLocalStorage();

        noteTaker.addNewNote("my first title", "my note details");
        noteTaker.addNewNote("my second title", "my second note details");

        noteTaker.showStoredNotes();

        final List<String> shownNotes = noteTaker.getDisplayedNoteTitles();

        assertEquals("my first title", shownNotes.get(0));
        assertEquals("my second title", shownNotes.get(1));
    }

    @Test
    public void canDeleteAllNotes(){

        FunctionalPage noteTaker = new FunctionalPage(driver);

        noteTaker.useLocalStorage();

        noteTaker.addNewNote("my first title", "my note details");
        noteTaker.addNewNote("my second title", "my second note details");

        noteTaker.showStoredNotes();

        assertEquals(2, noteTaker.getDisplayedNoteTitles().size());

        noteTaker.clearStoredNotes();
        noteTaker.showStoredNotes();

        assertEquals(0, noteTaker.getDisplayedNoteTitles().size());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
