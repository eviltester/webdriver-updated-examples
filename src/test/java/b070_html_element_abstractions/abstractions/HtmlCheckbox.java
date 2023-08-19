package b070_html_element_abstractions.abstractions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

public class HtmlCheckbox implements WrapsElement {
    private final WebElement element;

    public HtmlCheckbox(WebElement webElement) {
        this.element = webElement;
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    public boolean isChecked() {
        return element.isSelected();
    }

    public void toggle() {
        element.click();
    }

    public void check() {
        if(isChecked()){
            return;
        }
        toggle();
    }

    public void uncheck() {
        if(!isChecked()){
            return;
        }
        toggle();
    }
}
