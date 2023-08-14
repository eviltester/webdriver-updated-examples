package a010_how_abstractions_help.abstractions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public class NotesAppUser {

    private final NotesAppNavigator navigate;
    private NotesPage notesAppPage;

    public NotesAppUser(final WebDriver driver) {
        navigate = new NotesAppNavigator(driver);
    }

    public NotesAppUser visitsNotePage(){
        notesAppPage = navigate.to().notesPage();
        return this;
    }

    public NotesAppUser createsANote(final String noteTitle, final String noteContent) {
        notesAppPage.enterNote(noteTitle, noteContent);
        return this;
    }

    public UserNote createsARandomNote() {
        String noteTitle = new ARandomString(" ").generate();
        String noteMemo = new ARandomString(" ").generate() + "\n" +
                        new ARandomString(" ").generate();
        createsANote(noteTitle, noteMemo);
        return new UserNote(noteTitle, noteMemo);
    }

    public boolean canSeeNote(final String noteTitle) {
        return notesAppPage.isNoteVisible(noteTitle);
    }

    public NotesAppUser and() {
        return this;
    }

    public void waitsUntilCanSeeNote(final UserNote aNote) {
        new FluentWait<NotesAppUser>(this).until(
                (aUser) -> {  return
                        aUser.canSeeNote(aNote.noteTitle()); });
    }
}
