package com.projeto.milton.multimidia.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.projeto.milton.multimidia.R;
import com.projeto.milton.multimidia.view.ImagemView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AnimacaoActivity extends AppCompatActivity {
    @InjectView(R.id.animacao_player)
    ImagemView imagem;
    @InjectView(R.id.animacao_btn_direita)
    Button btn_dir;
    @InjectView(R.id.animacao_btn_esquerda)
    Button btn_cima;
    @InjectView(R.id.animacao_btn_cima)
    Button btn_esq;
    @InjectView(R.id.animacao_btn_baixo)
    Button btn_baixo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animacao);

        ButterKnife.inject(this);
    }

    @OnClick(R.id.animacao_btn_esquerda)
    public void moverEsq(){
        imagem.mover("esquerda");
    }

    @OnClick(R.id.animacao_btn_direita)
    public void moverDir(){
        imagem.mover("direita");
    }

    @OnClick(R.id.animacao_btn_cima)
    public void moverCima(){
        imagem.mover("cima");
    }

    @OnClick(R.id.animacao_btn_baixo)
    public void moverBaixo(){
        imagem.mover("baixo");
    }
}
