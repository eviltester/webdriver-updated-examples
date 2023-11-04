package b070_html_element_abstractions;

import a005_basic_webdriver_and_junit.Driver;
import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import b070_html_element_abstractions.abstractions.FileInputElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class FileUploadAbstractionTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser(){
        // driver = new ChromeDriver();
        driver = Driver.create();
        driver.get(new SiteUrls(new Environment()).htmlForm());
    }

    @Test
    public void canInteractWithFileInputAsWebElement() throws URISyntaxException {
        final By fileNameInput = By.cssSelector("input[name='filename']");
        final WebElement fileInputElement
                = driver.findElement(fileNameInput);

        fileInputElement.clear();
        assertEquals("", fileInputElement.getAttribute("value"));

        final URL filePathUrl = FileUploadAbstractionTest.class.getResource("textfile.txt");
        final String filePath = Paths.get(filePathUrl.toURI()).toFile().getAbsolutePath();

        fileInputElement.sendKeys(filePath);

        assertTrue(fileInputElement.getAttribute("value").endsWith("textfile.txt"));
    }

    @Test
    public void canInteractWithFileInputSemantically() throws URISyntaxException {
        final By fileNameInput = By.cssSelector("input[name='filename']");
        final WebElement fileInputElement
                = driver.findElement(fileNameInput);

        final FileInputElement fileInput = new FileInputElement(fileInputElement);

        final URL filePathUrl = FileUploadAbstractionTest.class.
                                    getResource("textfile.txt");
        final File file = Paths.get(filePathUrl.toURI()).toFile();

        fileInput.chooseFile(file);

        assertTrue(fileInput.value().endsWith("textfile.txt"));

        fileInput.clear();
        assertEquals("", fileInputElement.getAttribute("value"));
    }

    @Test
    public void missingFileDetected(){
        final By fileNameInput = By.cssSelector("input[name='filename']");
        final WebElement fileInputElement
                = driver.findElement(fileNameInput);

        final FileInputElement fileInput = new FileInputElement(fileInputElement);


        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                fileInput.chooseFile(new File("textfile.txt")));

        assertTrue(thrown.getMessage().startsWith("File does not exist"));

        // a missing file with raw webdriver, no error is thrown and no value is added
        assertEquals("", fileInputElement.getAttribute("value"));
    }

    @Test
    public void canSubmitFormWithFile() throws URISyntaxException {
        final By fileNameInput = By.cssSelector("input[name='filename']");
        final WebElement fileInputElement
                = driver.findElement(fileNameInput);

        final FileInputElement fileInput = new FileInputElement(fileInputElement);

        final URL filePathUrl = FileUploadAbstractionTest.class.
                getResource("textfile.txt");
        final File file = Paths.get(filePathUrl.toURI()).toFile();

        fileInput.chooseFile(file);

        driver.findElement(By.cssSelector("input[type='submit']")).click();
        assertEquals("textfile.txt",driver.findElement(By.id("_valuefilename")).getText());

    }

    @AfterEach
    public void closeBrowser(){
        driver.close();
    }
}
