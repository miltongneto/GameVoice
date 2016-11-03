package com.projeto.milton.multimidia;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto.milton.multimidia.ui.AnimacaoActivity;
import com.projeto.milton.multimidia.ui.VoiceRecognitionTeste;
import com.projeto.milton.multimidia.view.ImagemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {
    @InjectView(R.id.btn_voice)
    ImageButton btn_voice;
    @InjectView(R.id.main_btn_speech)
    Button btn_speech;
    @InjectView(R.id.txt_voice)
    TextView txt_voice;
    @InjectView(R.id.main_btn_animacao)
    Button btn_animacao;

    @InjectView(R.id.animacao_player)
    ImagemView imagem;

    private boolean recognizarOn;

    private static final int REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        recognizarOn = false;

    }

    @OnClick(R.id.btn_voice)
    public void voice(View view){
        recognizarOn = true;
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0);
        if(activities.size() == 0){
            Toast.makeText(this, "O reconhecedor de voz nao esta disponivel",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Reconhecimento funcionando!", Toast.LENGTH_SHORT).show();
            recognize();
        }
    }

    @OnClick(R.id.main_btn_speech)
    public void speech(View view){
        Intent intent = new Intent(this, VoiceRecognitionTeste.class);
        startActivity(intent);
    }

    @OnClick(R.id.main_btn_animacao)
    public void animacao(){
        Intent intent = new Intent(this, AnimacaoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(recognizarOn)
            recognize();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }
    public void recognize(){
        Intent intent = new Intent (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"pt-BR");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"aguardando");
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Log.i("RecognizerIntent","Funcionou");

            if(results != null){
                String result = results.get(0);
                Log.i("Testando",result);
                txt_voice.setText(result);

                imagem.mover(result);

                if(result.equals("parar")) recognizarOn = false;
            }
        }else{
            Log.i("RecognizerIntent","Erro");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
