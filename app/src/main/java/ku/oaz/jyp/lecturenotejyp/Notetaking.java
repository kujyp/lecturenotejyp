package ku.oaz.jyp.lecturenotejyp;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by JYP on 16. 7. 22..
 */
public class Notetaking {
    private ArrayList<Note> notes;

    Notetaking(){
        this.notes = new ArrayList<Note>();
    }

    Notetaking(Note note) {
        this.notes = new ArrayList<Note>();
        this.notes.add(note);
    }

    public void add(Note note)
    {
        this.notes.add(note);
    }

    public ArrayList<Note> get_list() {
        return this.notes;
    }

    public String get_context() { return this.notes.get(0).get_context(); } // todo 구현

    public Note get(int index) {
        return this.notes.get(index);
    }

    public int length() {
        return this.notes.size();
    }
}
