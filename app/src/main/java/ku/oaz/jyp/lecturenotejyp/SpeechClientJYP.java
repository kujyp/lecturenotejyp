package ku.oaz.jyp.lecturenotejyp;

import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.naver.speech.clientapi.SpeechConfig;

import ku.oaz.jyp.lecturenotejyp.utils.AudioWriterPCM;
import ku.oaz.jyp.lecturenotejyp.utils.NaverRecognizer;

/**
 * Created by JYP on 16. 7. 22..
 */
public class SpeechClientJYP {
    private static SpeechConfig SPEECH_CONFIG;


    private NaverRecognizer naverRecognizer;

    private static AudioWriterPCM writer;
    private static String writer_filename = "Test";

    public static boolean isRunning;
    private static String mResult;

    private static Handler m_Handler;

    //JYP

    SpeechClientJYP(String client_id, String speech_config, Activity mainactivity, Handler m_handler) {
        isRunning = false;
        mResult = "";
        m_Handler = m_handler;

        if (speech_config == "EN")
            SPEECH_CONFIG = SpeechConfig.OPENAPI_EN;
        else
            SPEECH_CONFIG = SpeechConfig.OPENAPI_KR;

        naverRecognizer = new NaverRecognizer(mainactivity, scl_handler, client_id, SPEECH_CONFIG);
        //Log.e("JYP/S", "NaverRecognizer Class Construction");
        naverRecognizer.getSpeechRecognizer().initialize();
        //Log.i("JYP/S", "CLIENT_ID:" + client_id);
    }


    public boolean startASR() {
        // return TRUE -> is just started / FALSE -> when it's running already
        //JYP
        if (!isRunning) {
            mResult = "";
            isRunning = true;

            //Log.e("JYP/S", "naverRecognizer.recognize() START");
            naverRecognizer.recognize();
            //Log.e("JYP/S", "naverRecognizer.recognize() END");

            return true;
        } else {
            return false;
        }
        //JYP
    }

    public void stopASR() {
        naverRecognizer.stop();
        // todo 구현
    }



    private static Handler scl_handler = new Handler() {
        public void handleMessage(Message scl_msg) {
            //Log.e("JYP/S", "scl_Handler START");
            Message m_msg;
            switch (scl_msg.what) {
                case R.id.clientReady:
                    // Now an user can speak.
                    m_msg = Message.obtain(m_Handler, 1, mResult); // 1 = Ready
                    m_Handler.sendMessage(m_msg);

                    writer = new AudioWriterPCM(
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/LNOTEAudioBuffer");
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
                    mResult = results[0];
                    m_msg = Message.obtain(m_Handler, 3, mResult); // 3 = finalResult
                    m_Handler.sendMessage(m_msg);
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
                    break;
            }
            //Log.e("JYP/S", "scl_Handler END");
        }
    };
}
