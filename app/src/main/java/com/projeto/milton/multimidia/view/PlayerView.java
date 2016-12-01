package com.projeto.milton.multimidia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import util.ResultEnum;

import static com.projeto.milton.multimidia.R.drawable.player;
import static com.projeto.milton.multimidia.R.drawable.player_esquerda;
import static com.projeto.milton.multimidia.R.drawable.player_esquerda_invertido;
import static com.projeto.milton.multimidia.R.drawable.playerinvertido;


/**
 * Created by Milton on 28/10/2016.
 */

public class PlayerView extends GameObjectView {
    private Drawable imagem;
    private int largura;
    private int altura;
    private int movimento = 200;
    private boolean inverted = false;

    public PlayerView(Context context){
        super(context);
        init();
    }
    public PlayerView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    @Override
    public void onDraw(Canvas canvas) {
        int x = (int) getX();
        int y = (int) getY();
        super.onDraw(canvas);
        imagem.setBounds(x, y, x+largura, y+altura);
        imagem.draw(canvas);
        invalidate();
    }

    public boolean mover(String direcao){
        int x = (int) getX();
        int y = (int) getY();

        loadImage(direcao);

        boolean moveu = false;
        if(inverted){
            if(direcao.equals("direita")) direcao = "esquerda";
            else if(direcao.equals("esquerda")) direcao = "direita";
            else if(direcao.equals("cima")) direcao = "baixo";
            else if(direcao.equals("baixo")) direcao = "cima";
        }

        switch (direcao){
            case "direita":
                setX(x + movimento);
                moveu = true;
                break;
            case "esquerda":
                setX(x - movimento);
                moveu = true;
                break;

            case "cima":
                setY(y - movimento);
                moveu = true;
                break;
            case "baixo":
                setY(y + movimento);
                moveu = true;
                break;
        }
        invalidate();
        return moveu;
    }

    public void init() {
        imagem = getContext().getDrawable(player);
        largura = 150;//imagem.getIntrinsicWidth();
        altura = 150;//imagem.getIntrinsicHeight();
        setX(400);
        setY(750);
    }

    public void loadImage(String direcao){
        if(inverted){
            if(direcao.equals("esquerda"))  imagem = getContext().getDrawable(player_esquerda_invertido);
            else if(direcao.equals("direita"))  imagem = getContext().getDrawable(playerinvertido);
        }else{
            if(direcao.equals("esquerda"))  imagem = getContext().getDrawable(player_esquerda);
            else if(direcao.equals("direita")) imagem = getContext().getDrawable(player);
        }
    }

    public int process() {
        return ResultEnum.CONTINUAR;
    }

    public int getAltura() {
        return altura;
    }

    public int getLargura() {
        return largura;
    }

    public void goTop(){
        inverted = true;
        setX(400);
        setY(25);
        imagem = getContext().getDrawable(playerinvertido);
    }

    public void gotBottom(){
        setX(400);
        setY(750);
    }
}
