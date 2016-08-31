package ku.oaz.jyp.lecturenotejyp.ASRActivity;

import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.naver.speech.clientapi.SpeechConfig;

import ku.oaz.jyp.lecturenotejyp.Notetaking.*;
import ku.oaz.jyp.lecturenotejyp.*;
import ku.oaz.jyp.lecturenotejyp.utils.AudioWriterPCM;
import ku.oaz.jyp.lecturenotejyp.utils.NaverRecognizer;

/**
 * Created by JYP on 16. 7. 22..
 */
public class SpeechClientJYP {
    private SpeechConfig SPEECH_CONFIG;


    private NaverRecognizer naverRecognizer;

    private AudioWriterPCM writer;
    private String writer_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LNOTEAudio";
    private String writer_filename = "Test";

    public boolean isRunning;
    private String mResult;

    private Handler m_Handler;

    private Notetaking notetaking;
    private long tic_time;
    private boolean ASRflag;

    SpeechClientJYP(String client_id, String speech_config, Activity mainactivity, Handler m_handler) {
        isRunning = false;
        mResult = "";
        m_Handler = m_handler;
        notetaking = new Notetaking();


        if (speech_config == "EN")
            SPEECH_CONFIG = SpeechConfig.OPENAPI_EN;
        else
            SPEECH_CONFIG = SpeechConfig.OPENAPI_KR;

        naverRecognizer = new NaverRecognizer(mainactivity, scl_handler, client_id, SPEECH_CONFIG);
        //Log.e("JYP/S", "NaverRecognizer Class Construction");
        naverRecognizer.getSpeechRecognizer().initialize();
        //Log.i("JYP/S", "CLIENT_ID:" + client_id);
    }


    public void startASR() {
        ASRflag = true;
        recognize();
    }

    public void stopASR() {
        ASRflag = false;
        naverRecognizer.stop();
        // todo 구현
    }

    private void restartASR() {
        if(ASRflag)
            recognize();
    }

    public void recognize() {
        // return TRUE -> is just started / FALSE -> when it's running already
        if (!isRunning) {
            mResult = "";
            isRunning = true;

            //Log.e("JYP/S", "naverRecognizer.recognize() START");
            naverRecognizer.recognize();
            tic();
        }
    }

    public void tic() {
        tic_time = System.currentTimeMillis();
    }

    public long toc() {
        return (System.currentTimeMillis() - tic_time);
    }


    private Handler scl_handler = new Handler() {
        public void handleMessage(Message scl_msg) {
            //Log.e("JYP/S", "scl_Handler START");
            Message m_msg;
            switch (scl_msg.what) {
                case R.id.clientReady:
                    // Now an user can speak.
                    m_msg = Message.obtain(m_Handler, 1, mResult); // 1 = Ready
                    m_Handler.sendMessage(m_msg);

                    writer = new AudioWriterPCM(writer_path);
                    writer.open(writer_filename);
                    break;

                case R.id.audioRecording:
                    writer.write((short[]) scl_msg.obj);
                    break;

                case R.id.partialResult:
                    // Extract obj property typed with String.
                    mResult = (String) (scl_msg.obj);
                    m_msg = Message.obtain(m_Handler, 0, mResult); // 0 -> Partial
                    m_Handler.sendMessage(m_msg);
                    // 0 => means 'msg.what'
                    break;

                case R.id.finalResult:
                    // Extract obj property typed with String array.
                    // The first element is recognition result for speech.
                    String[] results = (String[]) scl_msg.obj;
                    outputResult(results);
                    break;

                case R.id.recognitionError:
                    if (writer != null) {
                        writer.close();
                    }

                    mResult = "Error code : " + scl_msg.obj.toString();
                    m_msg = Message.obtain(m_Handler, 0, mResult);
                    m_Handler.sendMessage(m_msg);
                    isRunning = false;
                    break;

                case R.id.clientInactive:
                    if (writer != null) {
                        writer.close();
                    }

                    isRunning = false;
                    m_msg = Message.obtain(m_Handler, 2); // 2 = Inactive
                    m_msg.sendToTarget();
                    restartASR();
                    break;
            }
            //Log.e("JYP/S", "scl_Handler END");
        }
    };

    private void outputResult(String[] results) {
        mResult = results[0];
        notewrite(mResult, toc());
        Message m_msg = Message.obtain(m_Handler, R.id.outputResult, mResult);
        m_Handler.sendMessage(m_msg);
    }

    public void set_path(String path) {
        this.writer_path = path;
    }

    public String get_path() {
        return this.writer_path;
    }

    public void set_filename(String filename) {
        this.writer_filename = filename;
    }

    public String get_filename() {
        return this.writer_filename;
    }

    private void notewrite(String mResult, long toc_time)
    {
        this.notetaking.add(new Note(mResult, (double)this.tic_time, (double)toc_time));
    }

    public String[] get_context()
    {
        return this.notetaking.get_context();
    }
}
