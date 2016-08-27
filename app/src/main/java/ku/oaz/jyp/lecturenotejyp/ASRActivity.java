package ku.oaz.jyp.lecturenotejyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ASRActivity extends Activity {
    private static final String CLIENT_ID = "vu7SHZ_wtYEU_j7nAfO5";
    private static final String SPEECH_CONFIG_KR = "KR"; // "KOREAN"
    private static final String SPEECH_CONFIG_EN = "EN"; // "ENGLISH"

    private static SpeechClientJYP SpeechClientJYP;

    private static View m_btnRecord;
    private static View m_btnStop;
    private static View m_btnPlay;
    private static TextView m_resultText;
    private static TextView m_resultTime;

    private static Notetaking notetaking;

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
    }

    static Handler m_handler = new Handler() {
        public void handleMessage(Message m_msg) {
            if (m_msg.what == 3)
                m_resultText.setText( SpeechClientJYP.get_context() );
        }// todo fix it ((Notetaking)m_msg.obj).get_context() ); }
    };

    Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.m_record:
                    SpeechClientJYP.startASR();
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
}
