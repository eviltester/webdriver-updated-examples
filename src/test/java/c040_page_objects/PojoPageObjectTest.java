package c040_page_objects;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import c040_page_objects.pojo.PojoNoteTakerPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PojoPageObjectTest {

    /*
        a POJO (Plain Old Java Object) Page Object

        Is a class that represents the page. There is no
        guidance as to how you create it, so it will often
        be a mix of functional, structural and locator items.

        The Page Object tends to evolve based on the needs of test,
        and when the application changes. So they can be inconsistent
        and cluttered.

        It can a good way to get started and refactor later
        when the application or approach stabilises.
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

        PojoNoteTakerPage noteTaker = new PojoNoteTakerPage(driver);

        noteTaker.useLocalStorage();
        noteTaker.loadFromStorage();
        noteTaker.addNewNote("my first title", "my note details");
        noteTaker.addNewNote("my second title", "my second note details");

        final List<String> shownNotes = noteTaker.getDisplayedNoteTitles();

        assertEquals("my first title", shownNotes.get(0));
        assertEquals("my second title", shownNotes.get(1));
    }

    @Test
    public void canCancelNoteCreation(){

        PojoNoteTakerPage noteTaker = new PojoNoteTakerPage(driver);

        noteTaker.useLocalStorage();
        noteTaker.loadFromStorage();
        noteTaker.enterNoteDetails("my first title", "my note details");
        noteTaker.clickCancelButton();

        noteTaker.waitTillHaveCancelledEdit();
        noteTaker.clickAddButton();

        final List<String> shownNotes = noteTaker.getDisplayedNoteTitles();

        assertEquals(0, shownNotes.size());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
