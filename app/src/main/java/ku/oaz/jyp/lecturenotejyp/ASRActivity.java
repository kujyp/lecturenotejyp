package ku.oaz.jyp.lecturenotejyp;

import android.app.Activity;
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

    private static boolean isRecording = false;
    private static Notetaking notetaking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asr);

        m_resultText = (TextView)findViewById(R.id.m_resultText);
        m_resultTime = (TextView)findViewById(R.id.m_resultTime);

        //OnClickListener #1
        m_btnRecord = findViewById(R.id.m_record);
        m_btnStop = findViewById(R.id.m_stop);
        m_btnPlay = findViewById(R.id.m_play);

        m_btnRecord.setOnClickListener(mClickListener);
        m_btnStop.setOnClickListener(mClickListener);
        m_btnPlay.setOnClickListener(mClickListener);
        //OnClickListener #1

        //Class Construction
        //Log.e("JYP/A", "Activity onCreate");
        SpeechClientJYP = new SpeechClientJYP(CLIENT_ID, SPEECH_CONFIG_EN, this, m_handler);
        //JYP0722 SpeechClientJYP = new SpeechClientJYP(CLIENT_ID, SPEECH_CONFIG, new Activity(), m_handler);
        //Log.e("JYP/A", "SpeechClientJYP Class Construction");
    }

    static Handler m_handler = new Handler() {
        public void handleMessage(Message m_msg) {

            //Log.e("JYP/A", "m_Handler START");
            switch(m_msg.what) {
                case 0: // Partial
                    //Log.i("JYP/A", (String)m_msg.obj);
                    m_resultText.setText(notetaking.summary() + (String) m_msg.obj);
                    m_resultTime.setText("분석중");
                    break;
                case 3: // finalResult
                    //Log.i("JYP/A", (String)m_msg.obj);
                    notetaking.add((String)m_msg.obj);
                    m_resultText.setText(notetaking.summary());
                    m_resultTime.setText("1문장 분석완료 - 현재는 인식이 안됩니다. 기다렸다가 말씀해주세요.");

                    break;
                case 1: // Ready
                    m_resultTime.setText("분석시작 - 말씀하세요");
                    break;
                case 2: // Inactive
                    if(isRecording) {
                        if(!SpeechClientJYP.isRunning) {
                            SpeechClientJYP.startASR();
                        }
                    }
                    break;
            }
            //Log.e("JYP/A", "m_Handler END");
        }
    };

    //OnClickListener #2
    Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.m_record:
                    //Log.e("JYP/A", "startASR START");
                    if(!SpeechClientJYP.isRunning) {
                        isRecording = true;
                        SpeechClientJYP.startASR();
                        notetaking = new Notetaking();
                        //JYP0722 notetaking = new Notetaking();
                    }
                    //Log.e("JYP/A", "startASR END");
                    break;
                case R.id.m_stop:
                    isRecording = false;
                    break;
                case R.id.m_play:
                    break;
            }

        }
    };
    //OnClickListener #2


}
