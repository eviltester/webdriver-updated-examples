package c045_combined_page_objects.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StructuralPage {

    private final WebDriver driver;
    private final LocatorPage locators;

    public StructuralPage(final WebDriver driver) {
        this.driver = driver;
        this.locators = new LocatorPage(driver);
    }

    public void clickLocalStorage() {
        locators.localStorageLink().click();
    }

    public void clickSessionStorage() {
        locators.sessionStorageLink().click();
    }

    public void clickLoadFromStorage() {
        locators.loadButton().click();
    }

    public void enterNoteTitle(final String title) {
        locators.find(locators.NOTE_TITLE_FIELD).sendKeys(title);
    }

    public void enterNoteDescription(final String note) {
        locators.find(locators.NOTE_DETAILS_FIELD).sendKeys(note);
    }

    public void clickAddButton() {
        locators.addButton().click();
    }

    public void clickCancelButton() {
        locators.cancelButton().click();
    }

    public void clickShowNotesButton() {
        locators.showButton().click();
    }

    public void clickClearButton() {
        locators.clearButton().click();
    }

    public List<WebElement> getDisplayedNoteElements(){
        return locators.findAll(locators.NOTE_IN_LIST);
    }

    public void waitTillStateDisplays(final String state) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.textToBe(
                        locators.NOTE_STATUS,
                        state));
    }


}
