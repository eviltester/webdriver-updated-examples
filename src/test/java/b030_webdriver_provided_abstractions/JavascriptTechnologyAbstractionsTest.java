package b030_webdriver_provided_abstractions;

import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.Storage;
import org.openqa.selenium.html5.WebStorage;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavascriptTechnologyAbstractionsTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.get(new SiteUrls(new Environment()).simpleNotesApp());
    }

    @Test
    public void createNoteUsingJavaScript(){

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // initially no notes are stored
        Storage storage = ((WebStorage) driver).getLocalStorage();
        assertEquals(0, storage.size());

        final Map<String, String> notes = Map.of(
                "title1", "note1",
                "title2", "note2",
                "title3", "note3");

        notes.forEach((title, note) ->
            js.executeScript(
            "document.notetaker.addNote(arguments[0],arguments[1])",
                title, note
                ));

        //now there should be three notes stored
        assertEquals(3, storage.size());

    }

    // script pinning
    @Test
    public void creatingAndUsingPinnedScripts(){

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // initially no notes are stored
        Storage storage = ((WebStorage) driver).getLocalStorage();
        assertEquals(0, storage.size());

        final ScriptKey createNoteScript = js.pin(
                "document.notetaker.addNote(arguments[0],arguments[1])");

        js.executeScript(createNoteScript, "title1", "note1");
        js.executeScript(createNoteScript, "title2", "note2");
        js.executeScript(createNoteScript, "title3", "note3");

        //now there should be three notes stored
        assertEquals(3, storage.size());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
