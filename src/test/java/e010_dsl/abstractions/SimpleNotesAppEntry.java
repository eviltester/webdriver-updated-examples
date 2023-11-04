package e010_dsl.abstractions;

import com.github.javafaker.Faker;

public class SimpleNotesAppEntry {

    private String title;
    private String note;

    public SimpleNotesAppEntry(String aTitle, String aNote){
        this.title = aTitle;
        this.note = aNote;
    }

    public SimpleNotesAppEntry(){
        this("","");
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public SimpleNotesAppEntry setTitle(String aTitle) {
        title = aTitle;
        return this;
    }

    public SimpleNotesAppEntry getNote(String aNote) {
        note = aNote;
        return this;
    }

    public static SimpleNotesAppEntry createRandomNoteEntry(){
        String aTitle = new Faker().lorem().sentence();

        if(aTitle.length()>50){
            aTitle = aTitle.substring(0,49);
        }

        String aNote =  new Faker().lorem().paragraph();

        return new SimpleNotesAppEntry(aTitle, aNote);
    }
}
