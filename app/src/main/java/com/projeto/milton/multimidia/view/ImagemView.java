package com.projeto.milton.multimidia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import static com.projeto.milton.multimidia.R.mipmap.ic_launcher;


/**
 * Created by Milton on 28/10/2016.
 */

public class ImagemView extends View {
    private Drawable imagem;
    private int x;
    private int y;
    private int largura;
    private int altura;
    private int movimento = 200;

    public ImagemView(Context context, AttributeSet attrs){
        super(context, attrs);
        imagem = context.getDrawable(ic_launcher);
        largura = imagem.getIntrinsicWidth();
        altura = imagem.getIntrinsicHeight();
        x = 150;
        y = 300;
        //setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        imagem.setBounds(x, y, x+largura, y+altura);
        imagem.draw(canvas);
    }

    public boolean mover(String direcao){
        boolean moveu = false;
        switch (direcao){
            case "direita":
                this.x = this.x + movimento;
                moveu = true;
                break;
            case "esquerda":
                this.x = this.x - movimento;
                moveu = true;
                break;
            case "cima":
                this.y = this.y - movimento;
                moveu = true;
                break;
            case "baixo":
                this.y = this.y + movimento;
                moveu = true;
                break;
        }
        invalidate();
        return moveu;
    }
}
