package b070_html_element_abstractions.abstractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

import java.util.List;

public class HtmlMultiSelect implements WrapsElement {

    private final WebElement element;
    private final List<WebElement> options;

    public HtmlMultiSelect(WebElement webElement) {
        element = webElement;
        options = element.findElements(By.tagName("option"));
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    public WebElement option(final int index) {
        return options.get(index);
    }

    public void select(final int index) {
        if(!options.get(index).isSelected()){
            options.get(index).click();
        }
    }

    public void unselect(final int index) {
        if(options.get(index).isSelected()){
            options.get(index).click();
        }
    }

    public void clear() {
        for (int index=0; index < options.size(); index++) {
            unselect(index);
        }
    }

    public int size() {
        return options.size();
    }

    public void selectAll() {
        for (int index=0; index < options.size(); index++) {
            select(index);
        }
    }
}
