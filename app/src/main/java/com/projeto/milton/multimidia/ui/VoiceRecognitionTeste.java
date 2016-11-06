package com.projeto.milton.multimidia.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.projeto.milton.multimidia.R;
import com.projeto.milton.multimidia.view.GameView;
import com.projeto.milton.multimidia.view.PlayerView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class VoiceRecognitionTeste extends Activity implements RecognitionListener {
    private static final int REQUEST_INTERNET = 200;

    @InjectView(R.id.speech_layout)
    LinearLayout layout;

    @InjectView(R.id.speech_game)
    GameView game;

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";

    private boolean recognizarOn;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognition);

        ButterKnife.inject(this);


        if (ContextCompat.checkSelfPermission(VoiceRecognitionTeste.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VoiceRecognitionTeste.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_INTERNET);
        }

        handler.post(runnable);
    }

    /*
    public void criarEnemy(){
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        imagemEnemy = new ImagemView(this);
        imagemEnemy.setLayoutParams(lparams);

        Drawable drawable = this.getDrawable(ic_notification_overlay);
        imagemEnemy.loadImagem(drawable,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),40,5,5);
        layout.addView(imagemEnemy);
    }

    public void loadEnemy(){
        Drawable drawable = this.getDrawable(ic_notification_overlay);
        enemy.loadImagem(drawable,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),40,5,5);
    }
    */

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(game.play()){
                handler.postDelayed(runnable,100);
                if(recognizarOn)
                    recognize();
            }
            else
                printMsg("Voce perdeu");
        }
    };

    public void printMsg (String msg){
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_speak)
    public void start(View v){
        recognizarOn = true;
        recognize();
    }
    public void stop(View v){
        recognizarOn = false;
    }

    public void recognize(){
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"pt-BR");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        speech.startListening(recognizerIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_INTERNET) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //start audio recording or whatever you planned to do
            }else if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(VoiceRecognitionTeste.this, Manifest.permission.RECORD_AUDIO)) {
                    //Show an explanation to the user *asynchronously*
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("This permission is important to record audio.")
                            .setTitle("Important permission required");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.requestPermissions(VoiceRecognitionTeste.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_INTERNET);
                        }
                    });
                    ActivityCompat.requestPermissions(VoiceRecognitionTeste.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_INTERNET);
                }else{
                    //Never ask again and handle your app without permission.
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        speech.stopListening();
        //speech.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches){
            text += result + "\n";
            game.getPlayer().mover(result);
        }
        Log.i("Text", text);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

}