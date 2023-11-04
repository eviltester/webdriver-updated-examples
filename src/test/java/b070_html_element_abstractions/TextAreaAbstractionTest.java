package b070_html_element_abstractions;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import b070_html_element_abstractions.abstractions.HtmlInputField;
import b070_html_element_abstractions.abstractions.HtmlTextAreaField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextAreaAbstractionTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(new SiteUrls(new Environment()).htmlForm());
    }

    @Test
    public void canInteractWithAnTextAreaAsWebElement(){
        final By commentsTextArea = By.cssSelector("textarea[name='comments']");
        final WebElement textAreaElement
                = driver.findElement(commentsTextArea);

        textAreaElement.clear();
        assertEquals("", textAreaElement.getAttribute("value"));

        textAreaElement.sendKeys("first line of text");
        textAreaElement.sendKeys("\n");
        textAreaElement.sendKeys("second line of text");

        assertEquals("first line of text\n" +
                        "second line of text",
                textAreaElement.getAttribute("value"));
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

    @Test
    public void textAreaAsExtensionOfInput(){

        final By commentsTextArea = By.cssSelector("textarea[name='comments']");
        final WebElement textAreaElement = driver.findElement(commentsTextArea);

        final HtmlTextAreaField textArea = new HtmlTextAreaField(textAreaElement);

        textArea.clear();

        textArea.addLine("this is my comment");
        textArea.addLine("spanning multiple lines");

        assertEquals("this is my comment\nspanning multiple lines",
                textArea.value());

        final String[] lines = textArea.getLines();

        assertEquals(2, lines.length);
        assertEquals("this is my comment", lines[0]);
        assertEquals("spanning multiple lines", lines[1]);
    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
