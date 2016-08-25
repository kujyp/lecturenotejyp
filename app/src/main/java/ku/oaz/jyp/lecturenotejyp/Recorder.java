package ku.oaz.jyp.lecturenotejyp;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.IOException;

/**
 * Created by JYP on 16. 8. 25..
 */
public class Recorder {
    MediaRecorder recorder = new MediaRecorder();
    String PATH_dir = Environment.getExternalStorageDirectory().getAbsolutePath();
    String path;

    Recorder() {
        this.recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        this.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    public void set_path(String RECORD_FILE) {
        //String RECORD_FILE = "/Rec01.wav";
        this.path = PATH_dir + RECORD_FILE;
        this.recorder.setOutputFile(this.path);
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

    public String get_path() {
        return this.path;
    }
}
