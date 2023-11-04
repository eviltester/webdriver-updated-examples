package d020_data_objects.abstractions;

import com.github.javafaker.Faker;

public class NoteEntry {

    String title;
    String note;

    public NoteEntry(String aTitle, String aNote){
        this.title = aTitle;
        this.note = aNote;
    }

    public NoteEntry(){
        this("","");
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public NoteEntry setTitle(String aTitle) {
        title = aTitle;
        return this;
    }

    public NoteEntry getNote(String aNote) {
        note = aNote;
        return this;
    }

    public static NoteEntry createRandomNoteEntry(){
        String aTitle = new Faker().lorem().sentence();

        if(aTitle.length()>50){
            aTitle = aTitle.substring(0,49);
        }

        String aNote =  new Faker().lorem().paragraph();

        return new NoteEntry(aTitle, aNote);
    }
}
