package ku.oaz.jyp.lecturenotejyp.ASRActivity;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.IOException;

/**
 * Created by JYP on 16. 8. 25..
 */
public class Recorder {
    MediaRecorder recorder = new MediaRecorder();
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LNOTEAudio";
    String filename = "Test";
    String fileext = ".3gp";

    Recorder() {
        this.recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        this.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    public void record() {
        try {
            this.recorder.prepare();
            this.recorder.start();
        } catch (IllegalStateException e) {
            // Toast.makeText(getBaseContext(), "error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.recorder.stop();
    }

    public void set_path(String path) {
        //String RECORD_FILE = "/Rec01.wav";
        this.path = path;
        this.recorder.setOutputFile(this.path + this.filename);
    }

    public String get_path() {
        return this.path;
    }

    public void set_filename(String filename) {
        this.filename = filename;
    }

    public String get_filename() {
        return this.filename;
    }
}
