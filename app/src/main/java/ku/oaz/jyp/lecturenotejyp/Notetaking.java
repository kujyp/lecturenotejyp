package ku.oaz.jyp.lecturenotejyp;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by JYP on 16. 7. 22..
 */
public class Notetaking {
    private ArrayList<String> arraySentence;
    //JYP0722 private ArrayList<Time> arrayTime;
    private int nSentence;

    Notetaking() {
        this.arraySentence = new ArrayList<String>();
        this.nSentence = 0;
    }

    public void add(String sentence)
    {
        if(!sentence.isEmpty()) {
            this.arraySentence.add(sentence);
            this.nSentence++;

            //Log.i("Note/Add " + this.nSentence, sentence);
            //Log.i("Note/Add " + this.nSentence, arraySentence.get(nSentence - 1));
        }
        else {
            //Log.i("Note/Add ", "NULL");
        }
    }

    public String summary() {
        String summary = "";
        //Log.i("Note/Sum "+this.nSentence,"1");
        for(int k1 = 0; k1 < this.nSentence; k1++) {
            summary = summary + arraySentence.get(k1) + " / ";
            //Log.i("Note/Sum", summary);
        }
        return summary;
    }
}
