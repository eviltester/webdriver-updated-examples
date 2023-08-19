package b070_html_element_abstractions.abstractions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

public class HtmlInputField implements WrapsElement {
    private final WebElement element;

    public HtmlInputField(WebElement webElement) {
        element = webElement;
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    public void clear() {
        this.element.clear();
    }

    public String value() {
        return this.element.getAttribute("value");
    }

    public void typeInto(final String textToType) {
        this.element.sendKeys(textToType);
    }

    public void value(final String textToSetAsValue) {
        clear();
        typeInto(textToSetAsValue);
    }
}
