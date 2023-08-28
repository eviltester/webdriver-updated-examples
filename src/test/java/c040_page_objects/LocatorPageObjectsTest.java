package c040_page_objects;

import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import c040_page_objects.locators.LocatorNoteTakerPage;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.Storage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LocatorPageObjectsTest {

    /*
     A Locator Page Object would only have the locators for
     used elements.

     It would expand as tests cover more of the page and be
     amended when the physical page changes.

     It would typically be used by other page objects rather than
     directly in a test as shown here.
    */
    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.get(new SiteUrls(new Environment()).simpleNotesApp());
    }

    @Test
    public void canCreateANewNote(){

        LocatorNoteTakerPage noteTaker = new LocatorNoteTakerPage(driver);

        noteTaker.localStorageLink().click();
        noteTaker.loadButton().click();

        noteTaker.find(LocatorNoteTakerPage.NOTE_TITLE_FIELD).
                sendKeys("my first title");
        noteTaker.find(LocatorNoteTakerPage.NOTE_DETAILS_FIELD).
                sendKeys("my note details");
        noteTaker.addButton().click();

        noteTaker.find(LocatorNoteTakerPage.NOTE_TITLE_FIELD).
                sendKeys("my second title");
        noteTaker.find(LocatorNoteTakerPage.NOTE_DETAILS_FIELD).
                sendKeys("my second details");
        noteTaker.addButton().click();

        final List<Map<String, String>> data = getLocalStoredData();

        assertEquals("my first title", data.get(0).get("title"));
        assertEquals("my second title", data.get(1).get("title"));
    }

    @Test
    public void canCancelNoteCreation(){

        LocatorNoteTakerPage noteTaker = new LocatorNoteTakerPage(driver);

        noteTaker.localStorageLink().click();
        noteTaker.loadButton().click();

        noteTaker.find(LocatorNoteTakerPage.NOTE_TITLE_FIELD).
                sendKeys("my first title");
        noteTaker.find(LocatorNoteTakerPage.NOTE_DETAILS_FIELD).
                sendKeys("my note details");

        noteTaker.cancelButton().click();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.textToBe(
                        LocatorNoteTakerPage.NOTE_STATUS,
                        "Cancelled Edit"));

        noteTaker.addButton().click();

        final List<Map<String, String>> storedNotes = getLocalStoredData();

        assertEquals(0, storedNotes.size());
    }

    private List<Map<String,String>> getLocalStoredData(){
        return getStorageStoredData(
                    ((WebStorage)driver).
                            getLocalStorage());
    }

    private List<Map<String,String>> getStorageStoredData(Storage storage){

        List<Map<String,String>> listOfNoteDetails = new ArrayList<>();

        String[] keys = storage.keySet().toArray(String[]::new);

        // simple sort into date field order by adding to TreeMap
        TreeMap<Double,Map<String,String>> sortedKeyMap = new TreeMap<>();

        for (String key : keys) {
            String storedValues = storage.getItem(key);
            Map noteValues = new Gson().fromJson(storedValues, Map.class);
            sortedKeyMap.put((Double)noteValues.get("date"), noteValues);
        }

        for(Map<String,String>vals : sortedKeyMap.values()){
            listOfNoteDetails.add(vals);
        }

        return listOfNoteDetails;
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
