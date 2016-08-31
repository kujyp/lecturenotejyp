package ku.oaz.jyp.lecturenotejyp.SoundPlayer;

import android.media.MediaPlayer;
import android.os.Environment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by JYP on 16. 8. 25..
 */
public class Player {
    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    String filename = "Test";
    private MediaPlayer mediaplayer;

    public Player() {
        this.mediaplayer = new MediaPlayer();
    }
    public Player(String path, String filename) {
        this.mediaplayer = new MediaPlayer();
        this.set_path(path);
        this.set_filename(filename);
    }

    public void set_path(String path) {
        this.path = path; }

    public String get_path() {
        return this.path;
    }

    public void set_filename(String filename) {
        this.filename = filename; }

    public String get_filename() {
        return this.filename;
    }

    public MediaPlayer get_mediaplayer() {
        return mediaplayer;
    }

    public boolean isplaying() {
        return this.mediaplayer.isPlaying();
    }

    public void play() {
        try {
            this.mediaplayer.setDataSource(this.path + this.filename);
            this.mediaplayer.prepare();
            this.mediaplayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean is_pcm(String path) {
        if(true) // todo 구현
            return true;
        else
            return false;
    }

    public static String convert_to_wav(String path, String pcm_filename) throws IOException {
        PCMtoWav pcmtowav = new PCMtoWav();
        String wav_filename = extension_pcm_to_wav(pcm_filename);

        try {
            pcmtowav.convert_to_wav(path + pcm_filename, path + wav_filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wav_filename;
    }

    public static String extension_pcm_to_wav(String pcm_path){
        return pcm_path.replace(".pcm", ".wav");
    }

    public void stop() {
        try {
            this.mediaplayer.stop();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void move(double sec) {
        int msec = (int)(sec*1000);

        this.mediaplayer.seekTo(msec);
    }

    public boolean playable() {
        if(this.mediaplayer.isPlaying() || this.path == null)
            return false;
        else
            return true;
    }
}
