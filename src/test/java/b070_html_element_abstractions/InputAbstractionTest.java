package b070_html_element_abstractions;

import b070_html_element_abstractions.abstractions.HtmlInputField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputAbstractionTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/basic-html-form-test.html");
    }

    @Test
    public void canInteractWithAnInputFieldAsWebElement(){
        final By userNameInput = new By.ByCssSelector("input[name='username']");
        final WebElement inputElement
                = driver.findElement(userNameInput);

        inputElement.sendKeys("adminuser");

        assertEquals("adminuser",
                inputElement.getAttribute("value"));

        inputElement.clear();

        assertEquals("",
                inputElement.getAttribute("value"));

        inputElement.sendKeys("admin");
        inputElement.sendKeys("user");
        assertEquals("adminuser",
                inputElement.getAttribute("value"));
    }


    @Test
    public void canInteractWithAnInputSemantically(){

        final By userNameInput = new By.ByCssSelector("input[name='username']");
        final WebElement inputElement = driver.findElement(userNameInput);

        final HtmlInputField input = new HtmlInputField(inputElement);

        input.clear();
        assertEquals("", input.value());

        input.typeInto("admin");
        input.typeInto("user");

        assertEquals("adminuser", input.value());

        input.value("admin");

        assertEquals("admin", input.value());
    }

    @Test
    public void canUseOnPassword(){

        final By userNameInput = new By.ByCssSelector("input[name='password']");
        final WebElement inputElement = driver.findElement(userNameInput);

        final HtmlInputField password = new HtmlInputField(inputElement);

        password.clear();
        password.value("mypassword");
        assertEquals("mypassword", password.value());
    }

    @Test
    public void textAreaSharesSameSemantics(){

        final By commentsTextArea = new By.ByCssSelector("textarea[name='comments']");
        final WebElement textAreaElement = driver.findElement(commentsTextArea);

        final HtmlInputField textArea = new HtmlInputField(textAreaElement);

        textArea.clear();
        textArea.value("this is my comment\nspanning multiple lines");
        assertEquals("this is my comment\nspanning multiple lines", textArea.value());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}