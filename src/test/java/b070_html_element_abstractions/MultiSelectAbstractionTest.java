package b070_html_element_abstractions;

import b070_html_element_abstractions.abstractions.HtmlMultiSelect;
import b070_html_element_abstractions.abstractions.HtmlRadioButton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiSelectAbstractionTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/basic-html-form-test.html");
    }

    @Test
    public void canInteractWithARadioButtonAsWebElement(){

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

//
//        // click works slightly different for multi select
//        // it doesn't clear those that are selected
//        radioButtonElements.get(0).click();
//
//        assertTrue(radioButtonElements.get(0).isSelected());
//        assertTrue(radioButtonElements.get(3).isSelected());
//
//        radioButtonElements.get(3).click();
//
//        assertTrue(radioButtonElements.get(0).isSelected());
//        assertFalse(radioButtonElements.get(1).isSelected());
//        assertFalse(radioButtonElements.get(2).isSelected());
//        assertFalse(radioButtonElements.get(3).isSelected());

    }

    @Test
    public void canInteractWithARadioButtonSemantically(){

        final By radioButtons = By.cssSelector("select[name='multipleselect[]'] > option");
        final WebElement aRadioButton = driver.findElement(radioButtons);

        final HtmlRadioButton radio = new HtmlRadioButton(aRadioButton);

        assertFalse(radio.isChecked());

        radio.check();

        assertTrue(radio.isChecked());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
