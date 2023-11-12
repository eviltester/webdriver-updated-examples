package a010_how_abstractions_help;

import a005_basic_webdriver_and_junit.Driver;
import a010_how_abstractions_help.abstractions.NotesAppNavigator;
import a010_how_abstractions_help.abstractions.NotesPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleHowAbstractionsHelpTest {

    @Test
    public void createANoteUsingAbstractions(){

        WebDriver driver = Driver.create();

        NotesAppNavigator navigate = new NotesAppNavigator(driver);
        NotesPage notespage = navigate.to().notesPage();

        notespage.enterNote(
                "My navigated note",
                "Contents of my note");

        assertTrue(notespage.isNoteVisible("My navigated note"));

        driver.close();
        driver.quit();
    }
}
