package a010_how_abstractions_help;

import a005_basic_webdriver_and_junit.Driver;
import a010_how_abstractions_help.abstractions.NotesAppUser;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HighLevelHowAbstractionsHelpTest {

    @Test
    public void createANoteUsingUserAbstractions(){

        WebDriver driver = Driver.create();

        NotesAppUser user = new NotesAppUser(driver);
        user.
                visitsNotePage().and().
                createsANote(
                        "My user note",
                        "Contents of my note");

        assertTrue(user.canSeeNote("My user note"));

        driver.close();
        driver.quit();
    }
}
