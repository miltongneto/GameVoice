package com.projeto.milton.multimidia;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.projeto.milton.multimidia.ui.VoiceRecognitionTeste;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @InjectView(R.id.main_btn_jogar)
    Button btn_speech;
    @InjectView(R.id.mudo)
    ImageButton imgMudo;
    MediaPlayer mediaPlayer;

    private boolean recognizarOn;
    private boolean tocar;

    private static final int REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        tocar=false;
        recognizarOn = false;

        play_music();

        imgMudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tocar==true){
                mudo();
                imgMudo.setBackgroundResource(R.drawable.ic_action_name_mudo);
                }
                else {
                    play_music();
                    imgMudo.setBackgroundResource(R.drawable.ic_action_name_tocar);
                }
            }
        });

    }
    @OnClick(R.id.main_btn_jogar)
    public void jogar(){
        mudo();
        Intent intent = new Intent(this, VoiceRecognitionTeste.class);
        startActivity(intent);
    }
    public void mudo(){
        if (tocar==true){
        mediaPlayer.stop();
        tocar=false;
        }
    }
    public void play_music(){
        if(tocar==false){
            mediaPlayer = MediaPlayer.create(this,R.raw.musica_abertura);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            tocar=true;
        }

    }


}
