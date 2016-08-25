package ku.oaz.jyp.lecturenotejyp;

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
        this.path = this.PATH_dir + path;
    }

    public String getPath() {
        return this.path;
    }

    public void play() {
        try {
            this.mediaplayer.setDataSource(path);
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
}
