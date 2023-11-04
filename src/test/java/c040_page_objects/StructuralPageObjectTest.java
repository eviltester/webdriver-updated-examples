package c040_page_objects;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import c040_page_objects.structural.StructuralNoteTakerPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StructuralPageObjectTest {

    /*
        a Structural Page Object

        The exposed methods typically follow the structure of
        the page and physical actions, rather than the 'user'
        focused functionality.
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

        StructuralNoteTakerPage noteTaker = new StructuralNoteTakerPage(driver);

        noteTaker.clickLocalStorage();
        noteTaker.clickLoadFromStorage();
        noteTaker.enterNoteDetails("my first title", "my note details");
        noteTaker.clickAddButton();
        noteTaker.enterNoteDetails("my second title", "my second note details");
        noteTaker.clickAddButton();

        final List<String> shownNoteTitles = noteTaker.getDisplayedNoteTitles();

        assertEquals("my first title", shownNoteTitles.get(0));
        assertEquals("my second title", shownNoteTitles.get(1));
    }

    @Test
    public void canCancelNoteCreation(){

        StructuralNoteTakerPage noteTaker = new StructuralNoteTakerPage(driver);

        noteTaker.clickLocalStorage();
        noteTaker.clickLoadFromStorage();
        noteTaker.enterNoteDetails("my first title", "my note details");
        noteTaker.clickCancelButton();

        noteTaker.waitTillStateDisplays(StructuralNoteTakerPage.CANCELLED_EDIT);
        noteTaker.clickAddButton();

        final List<String> shownNoteTitles = noteTaker.getDisplayedNoteTitles();

        assertEquals(0, shownNoteTitles.size());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
