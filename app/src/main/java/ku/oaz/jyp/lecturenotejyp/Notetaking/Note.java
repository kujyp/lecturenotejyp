package ku.oaz.jyp.lecturenotejyp.Notetaking;

/**
 * Created by JYP on 16. 8. 24..
 */
public class Note {
    private String note_context;
    private double note_startingpoint;
    private double note_duration;

    public Note(String note_context, double note_startingpoint, double note_duration){
        this.note_context = note_context;
        this.note_startingpoint = note_startingpoint;
        this.note_duration = note_duration;
    }

    public String get_context(){
        return this.note_context;
    }

    public double get_startingpoint() {
        return this.note_startingpoint;
    }

    public double get_duration() {
        return this.note_duration;
    }
}
