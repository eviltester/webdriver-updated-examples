package e010_dsl.abstractions;

import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import org.openqa.selenium.WebDriver;

public class SimpleNotesAppNavigator {
    private final WebDriver driver;

    public SimpleNotesAppNavigator(final WebDriver driver) {
        this.driver = driver;
    }

    public SimpleNotesAppPage notesPage() {
        driver.get(new SiteUrls(new Environment()).simpleNotesApp());
        return new SimpleNotesAppPage(driver);
    }

    public SimpleNotesAppNavigator to(){
        return this;
    }

}
