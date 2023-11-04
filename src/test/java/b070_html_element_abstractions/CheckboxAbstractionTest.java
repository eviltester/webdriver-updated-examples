package b070_html_element_abstractions;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import b070_html_element_abstractions.abstractions.HtmlCheckbox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckboxAbstractionTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(new SiteUrls(new Environment()).htmlForm());
    }

    @Test
    public void canInteractWithACheckBoxAsWebElement(){

        final By checkboxes = By.cssSelector("input[type='checkbox']");

        final WebElement aCheckbox = driver.findElement(checkboxes);

        assertFalse(aCheckbox.isSelected());

        aCheckbox.click();

        assertTrue(aCheckbox.isSelected());

        aCheckbox.click();

        assertFalse(aCheckbox.isSelected());

    }

    @Test
    public void canInteractWithACheckBoxSemantically(){

        final By checkboxes = By.cssSelector("input[type='checkbox']");

        final WebElement aCheckbox = driver.findElement(checkboxes);

        final HtmlCheckbox checkbox = new HtmlCheckbox(aCheckbox);

        assertFalse(checkbox.isChecked());

        checkbox.check();

        assertTrue(checkbox.isChecked());

        checkbox.uncheck();

        assertFalse(checkbox.isChecked());

        checkbox.toggle();

        assertTrue(checkbox.isChecked());
    }


    @Test
    public void canInteractWithCheckBoxSemantics(){

        final By checkboxes = By.cssSelector("input[type='checkbox']");

        final List<WebElement> theCheckboxElements = driver.findElements(checkboxes);

        HtmlCheckbox checkbox1 = new HtmlCheckbox(theCheckboxElements.get(0));
        HtmlCheckbox checkbox2 = new HtmlCheckbox(theCheckboxElements.get(1));
        HtmlCheckbox checkbox3 = new HtmlCheckbox(theCheckboxElements.get(2));

        // check the default checkbox states
        assertFalse(checkbox1.isChecked());
        assertFalse(checkbox2.isChecked());
        assertTrue(checkbox3.isChecked());


        checkbox1.toggle();
        checkbox2.toggle();
        checkbox3.toggle();

        // check the default checkbox states
        assertTrue(checkbox1.isChecked());
        assertTrue(checkbox2.isChecked());
        assertFalse(checkbox3.isChecked());

        checkbox1.uncheck();
        checkbox2.uncheck();
        checkbox3.uncheck();

        // check the default checkbox states
        assertFalse(checkbox1.isChecked());
        assertFalse(checkbox2.isChecked());
        assertFalse(checkbox3.isChecked());

        checkbox1.check();
        checkbox2.check();
        checkbox3.check();

        // check the default checkbox states
        assertTrue(checkbox1.isChecked());
        assertTrue(checkbox2.isChecked());
        assertTrue(checkbox3.isChecked());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
