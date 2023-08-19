package b070_html_element_abstractions.abstractions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

public class HtmlRadioButton  implements WrapsElement {

    private final WebElement element;

    public HtmlRadioButton(WebElement webElement) {
        element = webElement;
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    public boolean isChecked() {
        return element.isSelected();
    }

    public void check() {
        element.click();
    }
}
