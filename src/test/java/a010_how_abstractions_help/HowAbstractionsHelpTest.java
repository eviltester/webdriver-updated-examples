package a010_how_abstractions_help;

import a005_basic_webdriver_and_junit.Driver;
import a010_how_abstractions_help.abstractions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HowAbstractionsHelpTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // driver = new ChromeDriver();
        driver = Driver.create();
    }

    @Test
    public void noAbstractions(){
        driver.get("https://testpages.eviltester.com/styled/apps/notes/simplenotes.html");

        final WebElement titleField =
                driver.findElement(
                        By.cssSelector("#note-title-input"));

        titleField.sendKeys("My basic note");

        final WebElement noteField =
                driver.findElement(
                        By.cssSelector("#note-details-input"));

        noteField.sendKeys("Details of basic note");

        final WebElement addButton = driver.findElement(By.id("add-note"));
        addButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("p.title-note-in-list"),1)
        );

        assertEquals("My basic note",
                driver.findElement(
                        By.cssSelector("p.title-note-in-list")).getText().trim());
    }


    @Test
    public void canCreateANoteUsingPageObjects(){

        driver.get(NotesAppSite.getNotesAppUrl());

        NotesPage notespage = new NotesPage(driver);
        notespage.enterNote("My page note", "Contents of my note");

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.numberOfElementsToBe(
                        NotesPage.NOTES_LIST_ITEM,1)
        );

        assertEquals("My page note",
                driver.findElement(
                        NotesPage.NOTES_LIST_ITEM).getText().trim());
    }


    @Test
    public void canCreateANote(){

        NotesAppNavigator navigate = new NotesAppNavigator(driver);
        NotesPage notespage = navigate.to().notesPage();

        notespage.enterNote(
                "My navigated note",
                "Contents of my note");

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                (driver) -> notespage.isNoteVisible("My navigated note")
        );
    }

    @Test
    public void aUserCanCreateANote(){
        NotesAppUser user = new NotesAppUser(driver);
        user.
            visitsNotePage().and().
            createsANote("My user note", "Contents of my note");

        new FluentWait<>(user).until(
                (aUser) -> aUser.canSeeNote("My user note"));
    }

    @Test
    public void aUserCanCreateARandomNote(){
        NotesAppUser user = new NotesAppUser(driver);

        UserNote aNote = user.
                visitsNotePage().and().
                createsARandomNote();

        user.waitsUntilCanSeeNote(aNote);
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
