package ku.oaz.jyp.lecturenotejyp.SoundPlayer;

import android.media.MediaPlayer;
import android.os.Environment;

import java.io.IOException;

/**
 * Created by JYP on 16. 8. 25..
 */
public class Player {
    String PATH_dir = Environment.getExternalStorageDirectory().getAbsolutePath();
    String path;
    MediaPlayer mediaplayer;

    Player() {
        this.mediaplayer = new MediaPlayer();
    }
    Player(String path) {
        this.mediaplayer = new MediaPlayer();
        this.setPath(path);
    }

    public void setPath(String path) {
        this.path = path; } // todo fix it this.path = this.PATH_dir + path; }

    public String getPath() {
        return this.path;
    }

    public void play() {
        try {
            this.mediaplayer.setDataSource(this.path);
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

    public String convert_to_wav(String pcm_path) throws IOException {
        PCMtoWav pcmtowav = new PCMtoWav();
        String wav_path = extension_pcm_to_wav(pcm_path);

        try {
            pcmtowav.convert_to_wav(pcm_path, wav_path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wav_path;
    }

    public String extension_pcm_to_wav(String pcm_path){
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
