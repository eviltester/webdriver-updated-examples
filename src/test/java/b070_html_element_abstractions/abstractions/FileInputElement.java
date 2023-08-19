package b070_html_element_abstractions.abstractions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

import java.io.File;

public class FileInputElement implements WrapsElement {
    private final WebElement element;

    public FileInputElement(final WebElement webElement) {
        element = webElement;
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    public void clear(){
        element.clear();
    }

    public void chooseFile(File file){

        if(!file.exists()){
            throw new RuntimeException("File does not exist " + file.getAbsolutePath());
        }

        element.sendKeys(file.getAbsolutePath());
    }

    public String value() {
        return element.getAttribute("value");
    }
}
