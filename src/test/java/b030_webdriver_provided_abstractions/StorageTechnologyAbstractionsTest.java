package b030_webdriver_provided_abstractions;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.Storage;
import org.openqa.selenium.html5.WebStorage;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTechnologyAbstractionsTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/apps/notes/simplenotes.html");
    }

    @Test
    public void createANoteAndAddToLocalStorage(){

        // localstorage is the default storage mechanism for the app

        // cast the driver to access the WebStorage interface
        WebStorage driverStorage = (WebStorage) driver;
        Storage storage = driverStorage.getLocalStorage();

        // initally storage is empty
        assertEquals(0, storage.size());

        final WebElement title = driver.findElement(By.id("note-title-input"));
        final WebElement note = driver.findElement(By.id("note-details-input"));

        title.sendKeys("Note title");
        note.sendKeys("Note details");
        driver.findElement(By.id("add-note")).click();

        assertEquals(1, storage.size());
        String[] keys = storage.keySet().toArray(String[]::new);

        // WebDriver uses Gson under the cover so we
        // can use this without adding any dependencies
        String storedValues = storage.getItem(keys[0]);
        Map noteValues = new Gson().fromJson(storedValues, Map.class);

        assertEquals("Note title", noteValues.get("title"));
        assertEquals("Note details", noteValues.get("note"));
    }

    @Test
    public void createANoteAndAddToSessionStorage(){

        // Both SessionStorage and LocalStorage implement
        // a Storage interface, so both the tests are
        // very similar

        // configure the app to use session storage
        driver.findElement(By.id("session-storage-link")).click();

        // cast the driver to access the WebStorage interface
        WebStorage driverStorage = (WebStorage) driver;
        Storage storage = driverStorage.getSessionStorage();

        // initally storage is empty
        assertEquals(0, storage.size());

        final WebElement title = driver.findElement(By.id("note-title-input"));
        final WebElement note = driver.findElement(By.id("note-details-input"));

        title.sendKeys("Session Note title");
        note.sendKeys("Session Note details");
        driver.findElement(By.id("add-note")).click();

        assertEquals(1, storage.size());
        String[] keys = storage.keySet().toArray(String[]::new);

        // WebDriver uses Gson under the cover so we
        // can use this without adding any dependencies
        String storedValues = storage.getItem(keys[0]);
        Map noteValues = new Gson().fromJson(storedValues, Map.class);

        assertEquals("Session Note title", noteValues.get("title"));
        assertEquals("Session Note details", noteValues.get("note"));
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
