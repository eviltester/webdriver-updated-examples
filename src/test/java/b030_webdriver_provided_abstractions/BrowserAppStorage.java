package b030_webdriver_provided_abstractions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BrowserAppStorage {

/**
 * Generic storage wrapper for localStorage or sessionStorage.
 */
    private final WebDriver driver;
    private final String storageType; // "localStorage" or "sessionStorage"

    public BrowserAppStorage(WebDriver driver, String storageType) {
        this.driver = driver;
        this.storageType = storageType;
    }

    public void setItem(String key, String value) {
        ((JavascriptExecutor) driver).executeScript(
                String.format("%s.setItem(arguments[0], arguments[1]);", storageType),
                key, value
        );
    }

    public String getItem(String key) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                String.format("return %s.getItem(arguments[0]);", storageType),
                key
        );
    }

    public void removeItem(String key) {
        ((JavascriptExecutor) driver).executeScript(
                String.format("%s.removeItem(arguments[0]);", storageType),
                key
        );
    }

    public void clear() {
        ((JavascriptExecutor) driver).executeScript(
                String.format("%s.clear();", storageType)
        );
    }

    @SuppressWarnings("unchecked")
    public Set<String> keySet() {

        List<String> values = (List<String>)((JavascriptExecutor) driver).executeScript(
                String.format(
                        "var keys = []; " +
                                "for (var i = 0; i < %s.length; i++) { keys.push(%s.key(i)); } " +
                                "return keys;", storageType, storageType
                ));

        Set<String> setValues = new HashSet();
        setValues.addAll(values);
        return setValues;
    }

    public int size() {
        Long result = (Long) ((JavascriptExecutor) driver).executeScript(
                String.format("return %s.length;", storageType)
        );
        return result.intValue();
    }

}
