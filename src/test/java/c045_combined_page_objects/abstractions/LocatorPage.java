package c045_combined_page_objects.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LocatorPage {

    /*
        Typically no synchronisation in the Page Object, but the method
        access could be used to add synchronization when necessary.
    */

    public static final By LOCAL_STORAGE_LINK = By.id("local-storage-link");
    public static final By SESSION_STORAGE_LINK = By.id("session-storage-link");

    public static final By NOTE_TITLE_FIELD = By.id("note-title-input");
    public static final By NOTE_DETAILS_FIELD = By.id("note-details-input");

    public static final By NOTE_STATUS = By.cssSelector("#note-status-details");

    public static final By ADD_BUTTON = By.id("add-note");
    public static final By UPDATE_BUTTON = By.id("update-note");
    public static final By CANCEL_BUTTON = By.id("cancel-note");

    public static final By LOAD_BUTTON = By.id("load-notes");
    public static final By SHOW_BUTTON = By.id("show-notes");
    public static final By CLEARALL_BUTTON = By.id("clear-notes");

    public static final By NOTE_IN_LIST = By.className("note-in-list");

    private final WebDriver driver;

    public LocatorPage(final WebDriver driver) {
        this.driver = driver;
    }

    public WebElement localStorageLink() {
        return driver.findElement(LOCAL_STORAGE_LINK);
    }

    public WebElement sessionStorageLink() {
        return driver.findElement(SESSION_STORAGE_LINK);
    }

    public WebElement addButton() {
        return driver.findElement(ADD_BUTTON);
    }

    public WebElement cancelButton() {
        return driver.findElement(CANCEL_BUTTON);
    }

    public WebElement loadButton() {
        return driver.findElement(LOAD_BUTTON);
    }

    public WebElement showButton() {
        return driver.findElement(SHOW_BUTTON);
    }

    public WebElement clearButton() {
        return driver.findElement(CLEARALL_BUTTON);
    }

    public WebElement find(By by){
        return driver.findElement(by);
    }

    public List<WebElement> findAll(By by){
        return driver.findElements(by);
    }


}
