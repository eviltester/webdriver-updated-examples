package b070_html_element_abstractions;

import b070_html_element_abstractions.abstractions.HtmlCheckbox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckboxAbstractionTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/basic-html-form-test.html");
    }

    @Test
    public void canInteractWithACheckBoxAsWebElement(){

        final By checkboxes = new By.ByCssSelector("input[type='checkbox']");

        final WebElement aCheckbox = driver.findElement(checkboxes);

        assertEquals(false, aCheckbox.isSelected());

        aCheckbox.click();

        assertEquals(true, aCheckbox.isSelected());

        aCheckbox.click();

        assertEquals(false, aCheckbox.isSelected());

    }

    @Test
    public void canInteractWithACheckBoxSemantically(){

        final By checkboxes = new By.ByCssSelector("input[type='checkbox']");

        final WebElement aCheckbox = driver.findElement(checkboxes);

        final HtmlCheckbox checkbox = new HtmlCheckbox(aCheckbox);

        assertEquals(false, checkbox.isChecked());

        checkbox.check();

        assertEquals(true, checkbox.isChecked());

        checkbox.uncheck();

        assertEquals(false, checkbox.isChecked());

        checkbox.toggle();

        assertEquals(true, checkbox.isChecked());
    }


    @Test
    public void canInteractWithCheckBoxSemantics(){

        final By checkboxes = new By.ByCssSelector("input[type='checkbox']");

        final List<WebElement> theCheckboxElements = driver.findElements(checkboxes);

        HtmlCheckbox checkbox1 = new HtmlCheckbox(theCheckboxElements.get(0));
        HtmlCheckbox checkbox2 = new HtmlCheckbox(theCheckboxElements.get(1));
        HtmlCheckbox checkbox3 = new HtmlCheckbox(theCheckboxElements.get(2));

        // check the default checkbox states
        assertEquals(false, checkbox1.isChecked());
        assertEquals(false, checkbox2.isChecked());
        assertEquals(true, checkbox3.isChecked());


        checkbox1.toggle();
        checkbox2.toggle();
        checkbox3.toggle();

        // check the default checkbox states
        assertEquals(true, checkbox1.isChecked());
        assertEquals(true, checkbox2.isChecked());
        assertEquals(false, checkbox3.isChecked());

        checkbox1.uncheck();
        checkbox2.uncheck();
        checkbox3.uncheck();

        // check the default checkbox states
        assertEquals(false, checkbox1.isChecked());
        assertEquals(false, checkbox2.isChecked());
        assertEquals(false, checkbox3.isChecked());

        checkbox1.check();
        checkbox2.check();
        checkbox3.check();

        // check the default checkbox states
        assertEquals(true, checkbox1.isChecked());
        assertEquals(true, checkbox2.isChecked());
        assertEquals(true, checkbox3.isChecked());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
