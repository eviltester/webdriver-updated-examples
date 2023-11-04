package b070_html_element_abstractions;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import b070_html_element_abstractions.abstractions.HtmlMultiSelect;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiSelectAbstractionTest {

    // WebDriver has an inbuilt support class called Select which
    // can be used for Multi Select items and Drop Down Select items
    // this class is a custom example of a multi select abstraction
    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(new SiteUrls(new Environment()).htmlForm());
    }

    @Test
    public void canInteractWithAMultiSelectAsWebElement() {

        final By multiSelect = By.cssSelector("select[name='multipleselect[]']");

        final WebElement multiSelectElement = driver.findElement(multiSelect);
        final List<WebElement> options =
                                    multiSelectElement.
                                        findElements(By.tagName("option"));

        assertFalse(options.get(0).isSelected());
        assertFalse(options.get(1).isSelected());
        assertFalse(options.get(2).isSelected());
        assertTrue(options.get(3).isSelected());

        // click works slightly different for multi select
        // than the manual click semantics
        // manual single click only selects that one item and clears others
        // WebDriver click is a ctrl+click, alt+click to select multiple
        options.get(0).click();
        assertTrue(options.get(0).isSelected());
        assertFalse(options.get(1).isSelected());
        assertFalse(options.get(2).isSelected());
        assertTrue(options.get(3).isSelected());

    }

        @Test
    public void canInteractWithAMultiSelectSemantically(){

        final By multiSelect = By.cssSelector("select[name='multipleselect[]']");

        final WebElement multiSelectElement = driver.findElement(multiSelect);

        final HtmlMultiSelect multi = new HtmlMultiSelect(multiSelectElement);

        multi.clear();

        assertFalse(multi.option(0).isSelected());
        assertFalse(multi.option(3).isSelected());

        assertEquals(4, multi.size());

        multi.select(0);

        assertTrue(multi.option(0).isSelected());
        assertFalse(multi.option(1).isSelected());
        assertFalse(multi.option(2).isSelected());
        assertFalse(multi.option(3).isSelected());

        multi.unselect(0);
        multi.select(1);

        assertFalse(multi.option(0).isSelected());
        assertTrue(multi.option(1).isSelected());
        assertFalse(multi.option(2).isSelected());
        assertFalse(multi.option(3).isSelected());

        multi.selectAll();

        assertTrue(multi.option(0).isSelected());
        assertTrue(multi.option(1).isSelected());
        assertTrue(multi.option(2).isSelected());
        assertTrue(multi.option(3).isSelected());

    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
