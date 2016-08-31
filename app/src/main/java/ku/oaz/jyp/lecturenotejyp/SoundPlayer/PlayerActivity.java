package ku.oaz.jyp.lecturenotejyp.SoundPlayer;

import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import ku.oaz.jyp.lecturenotejyp.*;
import ku.oaz.jyp.lecturenotejyp.Notetaking.*;
import ku.oaz.jyp.lecturenotejyp.Notetaking.Notetakings.*;
import ku.oaz.jyp.lecturenotejyp.ASRActivity.*;

public class PlayerActivity extends Activity {
    private View m_btnPlay;
    private TextView m_resultText;
    private Player[] player;
    private Player current_player;

    private Notetaking notetaking;

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LNOTEAudio/";;
    private String filename = "Test";
    private String fileext = ".pcm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        m_resultText = (TextView)findViewById(R.id.m_resultText);
        m_resultText.setOnClickListener(mClickListener);

        m_btnPlay = findViewById(R.id.m_play);
        m_btnPlay.setOnClickListener(mClickListener);


        read(path + filename);
        set_player(path, filename);// todo remove it
    }

    Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.m_play:
                    play();
                    break;
//                case R.id.m_open:
//                    path = open_file();
//                    read(path);
//                    set_player(path());
//                    break;
                case R.id.m_resultText:
                    break;
            }
        }
    };

    public void read(String pathNfilename) {
        try {
            FileInputStream fin = new FileInputStream(pathNfilename);
            ObjectInputStream ois = new ObjectInputStream(fin);

            try {
                this.set_notetaking((Notetaking) ois.readObject());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(int no) {
        while (true) {
            if (!this.isPlaying()) {
                this.player[no].play();
                this.current_player = this.player[no];
                break;
            }
        }
    }

    public boolean isPlaying() {
        if(this.get_current_player() != null) {
            return this.get_current_player().isplaying();
        }
        else {
            return false;
        }
    }
    public Player get_current_player() {
        return this.current_player;
    }

    public void play() {
        for (int k1 = 0; k1 < this.get_Nnotes(); k1++) {
            play(k1);
        }
    }

    public void set_player(String path, String filename) {
        this.player = new Player[this.get_Nnotes()];
        for(int k1 = 1; k1 <= this.get_Nnotes(); k1++) {
            try {
                String wav_filename = Player.convert_to_wav(path, filename + Integer.toString(k1) + fileext);
                this.player[k1-1] = open_file_withpath(path, wav_filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int get_Nnotes() {
        if (this.notetaking != null) {
            return this.notetaking.length(); }
        else {
            return 0;}
    }

    public void set_notetaking(Notetaking notetaking) {
        this.notetaking = notetaking;
    }
    public Notetaking get_notetaking() {
        return this.notetaking;
    }

    private Player open_file_withpath(String path, String filename) {
        return new Player(path, filename);
    }
//    private void initData() {
//        String path = "/";
//        getChildren().clear();
//        Player mediaPlayer = new Player(path);
//        setStyle("-fx-background-color: #bfc2c7;");
//        if (isVideo) {
//            mediaView = new MediaView(mediaPlayer);
//            mediaView.setFitHeight(350);
//            mediaView.setFitWidth(450);
//            getChildren().add(mediaView);
//        }
//        mediaBar = new HBox();
//        mediaBar.setMinWidth(450);
//        mediaBar.setAlignment(Pos.CENTER);
//        mediaBar.setPadding(new Insets(5, 10, 5, 10));
//        BorderPane.setAlignment(mediaBar, Pos.CENTER);
//
//        final Button playButton = new Button(">");
//
//        playButton.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent e) {
//                Status status = mediaPlayer.getStatus();
//
//                if (status == Status.UNKNOWN || status == Status.HALTED) {
//                    // don't do anything in these states
//                    return;
//                }
//
//                if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
//                    // rewind the movie if we're sitting at the end
//                    if (atEndOfMedia) {
//                        mediaPlayer.seek(mediaPlayer.getStartTime());
//                        atEndOfMedia = false;
//                    }
//                    mediaPlayer.play();
//                } else {
//                    mediaPlayer.pause();
//                }
//            }
//        });
//        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
//            public void invalidated(Observable ov) {
//                updateValues();
//            }
//        });
//
//        mediaPlayer.setOnPlaying(new Runnable() {
//            public void run() {
//                if (stopRequested) {
//                    mediaPlayer.pause();
//                    stopRequested = false;
//                } else {
//                    playButton.setText("||");
//                }
//            }
//        });
//
//        mediaPlayer.setOnPaused(new Runnable() {
//            public void run() {
//                playButton.setText(">");
//            }
//        });
//
//        mediaPlayer.setOnReady(new Runnable() {
//            public void run() {
//                duration = mediaPlayer.getMedia().getDuration();
//                updateValues();
//            }
//        });
//
//        mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
//        mediaPlayer.setOnEndOfMedia(new Runnable() {
//            public void run() {
//                if (!repeat) {
//                    playButton.setText(">");
//                    stopRequested = true;
//                    atEndOfMedia = true;
//                }
//            }
//        });
//
//        mediaBar.getChildren().add(playButton);
//        // Add spacer
//        Label spacer = new Label("   ");
//        mediaBar.getChildren().add(spacer);
//
//        // Add Time label
//        Label timeLabel = new Label("Time: ");
//        mediaBar.getChildren().add(timeLabel);
//
//        // Add time slider
//        timeSlider = new Slider();
//        HBox.setHgrow(timeSlider, Priority.ALWAYS);
//        timeSlider.setMinWidth(50);
//        timeSlider.setMaxWidth(Double.MAX_VALUE);
//        timeSlider.valueProperty().addListener(new InvalidationListener() {
//            public void invalidated(Observable ov) {
//                if (timeSlider.isValueChanging()) {
//                    // multiply duration by percentage calculated by slider
//                    // position
//                    mediaPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
//                }
//            }
//        });
//        mediaBar.getChildren().add(timeSlider);
//
//        // Add Play label
//        playTime = new Label();
//        playTime.setPrefWidth(100);
//        playTime.setMinWidth(50);
//        mediaBar.getChildren().add(playTime);
//
//        // Add the volume label
//        Label volumeLabel = new Label("Vol: ");
//        mediaBar.getChildren().add(volumeLabel);
//
//        // Add Volume slider
//        volumeSlider = new Slider();
//        volumeSlider.setPrefWidth(70);
//        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
//        volumeSlider.setMinWidth(30);
//        volumeSlider.valueProperty().addListener(new InvalidationListener() {
//            public void invalidated(Observable ov) {
//                if (volumeSlider.isValueChanging()) {
//                    mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
//                }
//            }
//        });
//        mediaBar.getChildren().add(volumeSlider);
//
//        getChildren().add(mediaBar);
//    }
}
