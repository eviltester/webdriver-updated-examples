package b070_html_element_abstractions;

import b070_html_element_abstractions.abstractions.HtmlCheckbox;
import b070_html_element_abstractions.abstractions.HtmlRadioButton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RadioButtonAbstractionTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/basic-html-form-test.html");
    }

    @Test
    public void canInteractWithARadioButtonAsWebElement(){

        final By radioButtons = By.cssSelector("input[type='radio']");

        final WebElement aRadioButton = driver.findElement(radioButtons);

        assertFalse(aRadioButton.isSelected());

        aRadioButton.click();

        assertTrue(aRadioButton.isSelected());

    }

    @Test
    public void canInteractWithARadioButtonSemantically(){

        final By radioButtons = By.cssSelector("input[type='radio']");
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
