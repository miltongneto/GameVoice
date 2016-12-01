package com.projeto.milton.multimidia.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto.milton.multimidia.R;
import com.projeto.milton.multimidia.ia.ProcessIA;
import com.projeto.milton.multimidia.view.GameView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import util.ResultEnum;


public class VoiceRecognitionTeste extends Activity implements RecognitionListener {
    private static final int REQUEST_INTERNET = 200;

    @InjectView(R.id.speech_layout)
    LinearLayout layout;

    @InjectView(R.id.speech_game)
    GameView game;

    @InjectView(R.id.speech_xp)
    TextView txt_xp;

    @InjectView(R.id.speech_message)
    TextView txt_message;

    private ProcessIA ia;

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognition";
    private boolean recognizarOn;

    final Handler handler = new Handler();
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognition);

        ButterKnife.inject(this);
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        txt_message.setText("");
        txt_xp.setTextSize(30);

        ia = new ProcessIA(this,game, height, width);

        if (ContextCompat.checkSelfPermission(VoiceRecognitionTeste.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VoiceRecognitionTeste.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_INTERNET);
        }

        handler.post(runnable);
    }


    public void alerta()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Voce perdeu");
        builder.setMessage("Voce fez " + " pontos");

        builder.setPositiveButton("Tentar Novamente", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                tryAgain();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void tryAgain() {startActivity(new Intent(this, VoiceRecognitionTeste.class));}

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int resultGame = ia.runGame();
            if(resultGame == ResultEnum.PERDEU){
                printMsg("Voce perdeu");
                somPerdeu(mediaPlayer);

               alerta();
            }else{
                if(resultGame == ResultEnum.LIE_LEFT){
                    txt_message.setText("ESQUERDA");
                }else if(resultGame == ResultEnum.LIE_RIGHT){
                    txt_message.setText("DIREITA");
                }else if(resultGame == ResultEnum.CONTINUAR){
                    txt_message.setText("");
                }else if(resultGame == ResultEnum.BONUS_XP){
                    pegarBonus(mediaPlayer);
                }
                txt_xp.setText("XP " + ia.getXp());
                handler.postDelayed(runnable,100);
                if(recognizarOn)
                    recognize();
            }
        }
    };

    public void printMsg (String msg){
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
    }
    public void somPerdeu(MediaPlayer mediaPlayer){
        mediaPlayer =MediaPlayer.create(this,R.raw.musica_game_over);
        mediaPlayer.start();
    }
    public void pegarBonus(MediaPlayer mediaPlayer){
        mediaPlayer =MediaPlayer.create(this,R.raw.musica_bonus);
        mediaPlayer.start();
    }

    @OnClick(R.id.btn_speak)
    public void start(View v){
        recognizarOn = true;
        Button button = (Button) findViewById(R.id.btn_speak);
        if (((ColorDrawable) button.getBackground()).getColor() == getResources().getColor(R.color.red))
        {
            button.setBackgroundColor(getResources().getColor(R.color.green));
            button.setText("Ouvindo");
        }
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