package e010_dsl;

import e010_dsl.abstractions.SimpleNotesAppEntry;
import e010_dsl.abstractions.SimpleNotesAppPage;
import e010_dsl.abstractions.SimpleNotesAppUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnExampleDslTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
    }

    @Test
    public void canCreateANote(){

        SimpleNotesAppEntry note = SimpleNotesAppEntry.createRandomNoteEntry();
        SimpleNotesAppUser user = new SimpleNotesAppUser(driver);

        user.
            visitsNotePage().
            and().
                usesLocalStorage().
                createsANote(note).
            then().
                waitsUntilCanSeeNote(note);

        assertTrue(
                new SimpleNotesAppPage(driver).
                        isNoteVisible(note.getTitle())
        );
    }


    @AfterEach
    public void closeBrowser(){
        driver.close();
    }

}
