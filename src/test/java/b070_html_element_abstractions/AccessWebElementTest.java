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

import static org.junit.jupiter.api.Assertions.*;

public class AccessWebElementTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/basic-html-form-test.html");
    }

    @Test
    public void canContinueToUseWebElementSemantics() {

        final By checkboxes = By.cssSelector("input[type='checkbox']");

        final List<WebElement> theCheckboxElements = driver.findElements(checkboxes);

        HtmlCheckbox checkbox1 = new HtmlCheckbox(theCheckboxElements.get(0));

        WebElement checkboxWebElement = checkbox1.getWrappedElement();

        // can check state using attribute or isSelected
        assertFalse(checkboxWebElement.isSelected());
        assertNull(checkboxWebElement.getAttribute("checked"));

        checkboxWebElement.click();
        assertTrue(checkboxWebElement.isSelected());

        // focus on the element and use keys to toggle state
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].focus()", checkboxWebElement);
        checkboxWebElement.sendKeys(" ");
        assertFalse(checkboxWebElement.isSelected());


        // set state entirely using JavaScript
        js.executeScript("arguments[0].checked=arguments[1]", checkboxWebElement, true);
        assertTrue(checkboxWebElement.isSelected());

        // check state entirely using JavaScript
        assertEquals(true,
                js.executeScript("return arguments[0].checked",checkboxWebElement));

    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
