package ku.oaz.jyp.lecturenotejyp.Notetaking;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by JYP on 16. 7. 22..
 */
public class Notetaking {
    private ArrayList<Note> notes;

    public Notetaking(){
        this.notes = new ArrayList<Note>();
    }

    public Notetaking(Note note) {
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

    public String[] get_context() {
        String[] context = new String[this.notes.size()];
        for(int k1 = 0; k1 < this.notes.size(); k1++) {
            context[k1] = this.notes.get(k1).get_context();
        }

        return context;
    }

    public Note get(int index) {
        return this.notes.get(index);
    }

    public int length() {
        return this.notes.size();
    }
}
