package b030_webdriver_provided_abstractions;

import org.openqa.selenium.WebDriver;


public class WebStorage {
    private final WebDriver driver;

    public WebStorage(WebDriver driver) {
        this.driver = driver;
    }

    public BrowserAppStorage localStorage() {
        return new BrowserAppStorage(driver, "localStorage");
    }

    public BrowserAppStorage sessionStorage() {
        return new BrowserAppStorage(driver, "sessionStorage");
    }
}
