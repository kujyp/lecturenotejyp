package ku.oaz.jyp.lecturenotejyp.ASRActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ku.oaz.jyp.lecturenotejyp.*;
import ku.oaz.jyp.lecturenotejyp.Notetaking.*;
import ku.oaz.jyp.lecturenotejyp.SoundPlayer.*;
import ku.oaz.jyp.lecturenotejyp.Notetaking.Notetakings.*;

public class ASRActivity extends Activity {
    private final String CLIENT_ID = "vu7SHZ_wtYEU_j7nAfO5";
    private final String SPEECH_CONFIG_KR = "KR"; // "KOREAN"
    private final String SPEECH_CONFIG_EN = "EN"; // "ENGLISH"

    private String abs_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LNOTEAudio/";
    private String abs_filename = "Test";

    private SpeechClientJYP SpeechClientJYP;
    private Recorder recorder;

    private View m_btnRecord;
    private View m_btnStop;
    private View m_btnPlay;
    private TextView m_resultText;
    private TextView m_resultTime;

    private Notetaking notetaking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asr);

        m_resultText = (TextView)findViewById(R.id.m_resultText);
        m_resultTime = (TextView)findViewById(R.id.m_resultTime);

        m_btnRecord = findViewById(R.id.m_record);
        m_btnStop = findViewById(R.id.m_stop);
        m_btnPlay = findViewById(R.id.m_play);

        m_btnRecord.setOnClickListener(mClickListener);
        m_btnStop.setOnClickListener(mClickListener);
        m_btnPlay.setOnClickListener(mClickListener);

        SpeechClientJYP = new SpeechClientJYP(CLIENT_ID, SPEECH_CONFIG_KR, this, m_handler);
        recorder = new Recorder();
    }

    Handler m_handler = new Handler() {
        public void handleMessage(Message m_msg) {
            if (m_msg.what == R.id.outputResult)
                outputResult();
        }// todo fix it ((Notetaking)m_msg.obj).get_context() ); }
    };

    Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
//                case R.id.m_enname:
//                    enname();
//                    break;
                case R.id.m_record:
                    enname(abs_path, abs_filename); //todo remove
                    record();
                    //Log.e("JYP/A", "startASR END");
                    break;
                case R.id.m_stop:
                    SpeechClientJYP.stopASR();
                    break;
                case R.id.m_play:
                    Intent intent = new Intent(ASRActivity.this, PlayerActivity.class);
                    startActivity(intent);
                    break;
            }

        }
    };

    public void outputResult() {
        m_resultText.setText( TextUtils.join("\n", SpeechClientJYP.get_context()) );
    }

    public void record() {
        SpeechClientJYP.startASR();
//        recorder.record();
    }

    public void stop() {
        SpeechClientJYP.stopASR();
//        recorder.stop();
    }

    public void enname(String path, String filename) {
        //todo get
        set_path(path);
        set_filename(filename);

    }

    public void set_path(String path) {
        this.SpeechClientJYP.set_path(path);
        this.recorder.set_path(path);
    }

    public void set_filename(String filename) {
        this.SpeechClientJYP.set_filename(filename);
        this.recorder.set_filename(filename);
    }
}
