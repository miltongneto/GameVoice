package com.projeto.milton.multimidia.ui;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.projeto.milton.multimidia.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VoiceRecognitionTeste extends Activity {
    private SpeechRecognizer sr;
    private static final String TAG = "MyStt3Activity";

    @InjectView(R.id.btn_speak)
    Button btn_speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognition);

        ButterKnife.inject(this);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());
    }

    @OnClick(R.id.btn_speak)
    public void speak(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"pt-BR");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"com.projeto.milton.multimidia.ui");
        sr.startListening(intent);
    }

    class listener implements RecognitionListener{

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Log.d(TAG,"onReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(TAG,"onBeginningOfSpeech");
        }

        @Override
        public void onRmsChanged(float v) {
            Log.d(TAG,"onRmsChanged");
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
            Log.d(TAG,"onBufferReceived");
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG,"onEndOfSpeech");
        }

        @Override
        public void onError(int error) {
            Log.d(TAG,"onError " + error);
        }

        @Override
        public void onResults(Bundle results) {
            String str = new String();
            Log.d(TAG,"onResults " + results);
            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for(int i=0;i<data.size();i++){
                Log.d(TAG,"result " + data.get(i));
                str += data.get(i);
            }
            Log.d(TAG,str.toString());
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            Log.d(TAG,"onPartialResults");
        }

        @Override
        public void onEvent(int eventType, Bundle bundle) {
            Log.d(TAG,"onEvent "+ eventType);
        }
    }
}
