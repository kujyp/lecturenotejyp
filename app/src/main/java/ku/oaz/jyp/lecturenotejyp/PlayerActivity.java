package ku.oaz.jyp.lecturenotejyp;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class PlayerActivity extends Activity {
    private static View m_btnPlay;
    private static TextView m_resultText;
    private static Player player;

    private static Notetaking notetaking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        m_resultText = (TextView)findViewById(R.id.m_resultText);
        m_resultText.setOnClickListener(mClickListener);

        m_btnPlay = findViewById(R.id.m_play);
        m_btnPlay.setOnClickListener(mClickListener);

        try {
            String test = "/storage/emulated/0/LNOTEAudioBuffer/Test.pcm";
            convert_and_play(test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.m_play:
                    if(player.playable())
                    {
                        player.play();
                    }
                    break;
//                case R.id.m_open:
//                    open_file(path);
//                    break;
                case R.id.m_resultText:
                    break;
            }
        }
    };

    private void convert_and_play(String path) throws IOException {
        try {
            String wav = player.convert_to_wav(path);
            open_file_withpath(wav);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void open_file_withpath(String path) {
        player = new Player(path);
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
