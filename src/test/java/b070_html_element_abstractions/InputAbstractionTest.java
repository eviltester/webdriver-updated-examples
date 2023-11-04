
package b070_html_element_abstractions;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
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
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(new SiteUrls(new Environment()).htmlForm());
    }

    @Test
    public void canInteractWithAnInputFieldAsWebElement(){
        final By userNameInput = By.cssSelector("input[name='username']");
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

        final By userNameInput = By.cssSelector("input[name='username']");
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

        final By userNameInput = By.cssSelector("input[name='password']");
        final WebElement inputElement = driver.findElement(userNameInput);

        final HtmlInputField password = new HtmlInputField(inputElement);

        password.clear();
        password.value("mypassword");
        assertEquals("mypassword", password.value());
    }

    @Test
    public void textAreaSharesSameSemantics(){

        final By commentsTextArea = By.cssSelector("textarea[name='comments']");
        final WebElement textAreaElement = driver.findElement(commentsTextArea);

        final HtmlInputField textArea = new HtmlInputField(textAreaElement);

        textArea.clear();
        textArea.value("this is my comment\nspanning multiple lines");
        assertEquals("this is my comment\nspanning multiple lines", textArea.value());
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
