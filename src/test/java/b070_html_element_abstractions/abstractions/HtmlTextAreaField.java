package b070_html_element_abstractions.abstractions;

import org.openqa.selenium.WebElement;

public class HtmlTextAreaField extends HtmlInputField{

    private final WebElement element;

    public HtmlTextAreaField(final WebElement textAreaElement) {
        super(textAreaElement);
        element = textAreaElement;
    }

    public void addLine(String aLineOfText){

        String linePrefix = "\n";

        if(value().equals("")){
            linePrefix="";
        }

        this.element.sendKeys(linePrefix + aLineOfText);
    }

    public String[] getLines(){
        return this.element.getAttribute("value").split("\n");
    }
}
