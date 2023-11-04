package e010_dsl.abstractions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public class SimpleNotesAppUser {

    private final SimpleNotesAppNavigator navigate;
    private SimpleNotesAppPage notesAppPage;

    public SimpleNotesAppUser(final WebDriver driver) {
        navigate = new SimpleNotesAppNavigator(driver);
    }

    public SimpleNotesAppUser visitsNotePage(){
        notesAppPage = navigate.to().notesPage();
        return this;
    }

    public SimpleNotesAppUser createsANote(SimpleNotesAppEntry aNote) {
        notesAppPage.enterNote(aNote);
        return this;
    }

    public SimpleNotesAppEntry createsARandomNote() {
        final SimpleNotesAppEntry aNote = SimpleNotesAppEntry.createRandomNoteEntry();
        createsANote(aNote);
        return aNote;
    }

    public boolean canSeeNote(SimpleNotesAppEntry aNote) {
        return notesAppPage.isNoteVisible(aNote.getTitle());
    }

    public SimpleNotesAppUser and() {
        return this;
    }

    public void waitsUntilCanSeeNote(final SimpleNotesAppEntry aNote) {
        new FluentWait<>(this).until(
                (aUser) -> aUser.canSeeNote(aNote)
        );
    }

    public SimpleNotesAppUser usesLocalStorage() {
        notesAppPage.useLocalStorage();
        return this;
    }

    public SimpleNotesAppUser then() {
        return this;
    }
}
