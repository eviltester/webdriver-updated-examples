package a010_how_abstractions_help.abstractions;

import org.openqa.selenium.WebDriver;

public class NotesAppNavigator {
    private final WebDriver driver;

    public NotesAppNavigator(final WebDriver driver) {
        this.driver = driver;
    }

    public NotesPage notesPage() {
        driver.get(NotesAppSite.getNotesAppUrl());
        return new NotesPage(driver);
    }

    public NotesAppNavigator to(){
        return this;
    }

}
