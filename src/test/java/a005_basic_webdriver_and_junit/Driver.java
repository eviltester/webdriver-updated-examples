package a005_basic_webdriver_and_junit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver {
    public static WebDriver create(){

        ChromeOptions options = new ChromeOptions();

        if(System.getenv().
                getOrDefault("BROWSER_STATE","show").
                equals("Headless")){
            options.addArguments("--headless");
        }

        return new ChromeDriver(options);
    }
}
