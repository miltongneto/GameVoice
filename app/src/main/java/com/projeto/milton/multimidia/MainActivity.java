package com.projeto.milton.multimidia;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.projeto.milton.multimidia.ui.VoiceRecognitionTeste;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @InjectView(R.id.main_btn_jogar)
    Button btn_speech;
    @InjectView(R.id.mudo)
    RelativeLayout layoutMudo;
    MediaPlayer mediaPlayer;

    private boolean recognizarOn;

    private static final int REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        recognizarOn = false;
        mediaPlayer =MediaPlayer.create(this,R.raw.musicaabertura);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        layoutMudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudo();
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
        mediaPlayer.stop();
    }


}
