package b030_webdriver_provided_abstractions;

import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HtmlElementAbstractions {

    // WebDriver is the main DOM element abstraction
    // the Select class in the support pages is a simple
    // HTML abstraction

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.get(new SiteUrls(new Environment()).htmlForm());
    }

    @Test
    public void useWebDriverSelectAbstractionOnMultiSelect(){

        WebElement multiSelect = driver.findElement(
                                    By.cssSelector("select[multiple='multiple']"));

        Select select = new Select(multiSelect);

        assertTrue(select.isMultiple());
        assertEquals("Selection Item 4",
                select.getFirstSelectedOption().getText());

        select.deselectByVisibleText("Selection Item 4");

        select.selectByIndex(0);
        select.selectByValue("ms2");
        select.selectByVisibleText("Selection Item 3");

        final List<WebElement> selected = select.getAllSelectedOptions();
        assertEquals(3, selected.size());
        assertEquals("Selection Item 1", selected.get(0).getText());
        assertEquals("Selection Item 2", selected.get(1).getText());
        assertEquals("Selection Item 3", selected.get(2).getText());
    }

    @Test
    public void useWebDriverSelectAbstractionOnDropdown(){

        WebElement dropdown = driver.findElement(
                By.cssSelector("select[name='dropdown']"));

        Select select = new Select(dropdown);

        assertFalse(select.isMultiple());

        assertEquals("Drop Down Item 3",
                select.getFirstSelectedOption().getText());

        // Not all Select operations are available for a drop down
        // e.g.
        // select.deselectByVisibleText("Drop Down Item 3");

        select.selectByIndex(0);
        assertEquals("Drop Down Item 1",
                select.getFirstSelectedOption().getText());

        select.selectByValue("dd2");
        assertEquals("Drop Down Item 2",
                select.getFirstSelectedOption().getText());

        select.selectByVisibleText("Drop Down Item 3");
        assertEquals("Drop Down Item 3",
                select.getFirstSelectedOption().getText());

        final List<WebElement> selected = select.getAllSelectedOptions();
        assertEquals(1, selected.size());
        assertEquals("Drop Down Item 3", selected.get(0).getText());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
