package com.projeto.milton.multimidia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import util.ResultEnum;

import static com.projeto.milton.multimidia.R.mipmap.ic_launcher;


/**
 * Created by Milton on 28/10/2016.
 */

public class PlayerView extends GameObjectView {
    private Drawable imagem;
    private int largura;
    private int altura;
    private int movimento = 200;

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
        boolean moveu = false;
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
        imagem = getContext().getDrawable(ic_launcher);
        largura = imagem.getIntrinsicWidth();
        altura = imagem.getIntrinsicHeight();
        setX(400);
        setY(750);
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
}
