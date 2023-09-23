package d010_navigation.loosecoupling;

import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import org.openqa.selenium.WebDriver;

public class CookieManagedDirectNav {
    private final WebDriver driver;

    public CookieManagedDirectNav(final WebDriver driver) {
        this.driver = driver;
    }

    public void loginPage() {
        driver.get(new SiteUrls(new Environment()).adminLoginExample());
    }
}
